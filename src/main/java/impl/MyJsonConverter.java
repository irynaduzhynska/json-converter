package impl;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

public class MyJsonConverter implements JsonConverterService {

    private Number value;
    private String theString;

    public String toJson(Object o) {
        if (o == null){
            return null;
        }
        Class clazz = o.getClass();
        if (o instanceof Integer || o instanceof Byte
        || o instanceof Long || o instanceof Double){
            return o.toString();
        }
        if (clazz == String.class){
            return "\"" + o.toString() + "\"";
        }
        if (clazz.isArray()){
            int length = Array.getLength(o);
            StringBuilder builder = new StringBuilder();
            builder.append("[");
            for (int i = 0; i < length; i++){
                Object e = Array.get(o,i);
                builder.append(toJson(e));
                if (i + 1 < length){
                    builder.append(",");
                }
            }
            builder.append("]");
            return builder.toString();
        }

        StringBuilder builder = new StringBuilder();
        builder.append("{");
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++){
            Field field = fields[i];
            if (!field.isAccessible()){
                field.setAccessible(true);
            }
            String name = field.getName();
            builder.append("\"" + name + "\"" + " : ");
            Object obj = null;
            try {
                obj = field.get(o);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            builder.append(toJson(obj));
            if (i + 1 < fields.length){
                builder.append(",");
            }
        }
        builder.append("}");
        return builder.toString();
    }

    public Object fromJson(String json, Class<?> clazz) {
        String s = "END";
        if (json == null){
            return null;
        }
        if (clazz.isPrimitive()){
            if (byte.class.equals(clazz) || Byte.class.equals(clazz)){
                try {
                    value = Byte.parseByte(json);
                    return value;
                }catch (NumberFormatException e){
                    System.out.println("Not correct format " +e);
                }
            }
            if (int.class.equals(clazz) || Integer.class.equals(clazz)){
                try {
                    value = Integer.parseInt(json);
                    return value;
                }catch (NumberFormatException e){
                    System.out.println("Not correct format " +e);
                }
            }
            if (long.class.equals(clazz) || Long.class.equals(clazz)){
                try {
                    value = Long.parseLong(json);
                    return value;
                }catch (NumberFormatException e){
                    System.out.println("Not correct format " +e);
                }
            }
            if (double.class.equals(clazz) || Double.class.equals(clazz)){
                try {
                    value = Double.parseDouble(json);
                    return value;
                }catch (NumberFormatException e){
                    System.out.println("Not correct format " +e);
                }
            }
        }
        if (clazz.equals(String.class)){
            theString = json;
            return theString;
        }
        /////////Convert an array///////////
        if (clazz.isArray()){
            if (json.charAt(0) != '[' && json.charAt(json.length()-1) != ']'){
                throw new IllegalArgumentException("Not correct format Json");
            }
            if (clazz.getComponentType().isPrimitive()) {
                int lengh = 1;
                for (char s1 : json.toCharArray()) {
                    if (s1 == ',') {
                        lengh++;
                    }
                }
                if (clazz.getComponentType().equals(Integer.class) || clazz.getComponentType().equals(int.class)) {
                    int[] theArray = new int[lengh];
                    return theArray;
                } else if (clazz.getComponentType().equals(Byte.class) || clazz.getComponentType().equals(byte.class)) {
                    byte[] theArray = new byte[lengh];
                    return theArray;
                } else if (clazz.getComponentType().equals(Double.class) || clazz.getComponentType().equals(double.class)) {
                    double[] theArray = new double[lengh];
                    return theArray;
                } else if (clazz.getComponentType().equals(Long.class) || clazz.getComponentType().equals(long.class)) {
                    long[] theArray = new long[lengh];
                    return theArray;
                }

            }
            /////If the array is a string/////
            if (clazz.getComponentType().equals(String.class)){
                String str = json.replaceAll("[,]","");
                char[] b = str.toCharArray();
                int countLength = 0;
                for (char i : b){
                    if (i == '"'){
                        countLength++;
                    }
                }
                String[] theArrayString  = new String[countLength/2];
                String word = "";
                for (int i = 2; i < b.length-1; i++){
                    if (b[i] != '\"'){
                        word+=b[i];
                    }else {
                        for (int j = 0; j < theArrayString.length; j++){
                            if (theArrayString[j] == null && word!=""){
                                theArrayString[j] = word;
                                word="";
                                j++;
                                break;
                            }
                        }
                    }
                }
                return theArrayString;
            }
            /////If the array is primitive/////
        }
        return s;
    }
}
