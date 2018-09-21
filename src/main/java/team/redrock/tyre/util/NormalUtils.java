package team.redrock.tyre.util;



import team.redrock.tyre.util.response.EmptyResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class NormalUtils {
    public String getSection(String SectionNum, EmptyResponse emptyResponse){
        String returnChar = null;
        if(isInteger(SectionNum)){

        switch(SectionNum){
            case "0": returnChar = "12";
            break;
            case "1": returnChar = "34";
            break;
            case "2": returnChar = "56";
            break;
            case "3": returnChar = "78";
            break;
            case "4": returnChar = "90";
            break;
            case "5": returnChar = "ab";
            break;
            default: returnChar = "null";
                break;
        }
        }else{
            returnChar = "null";
            emptyResponse.setStatus(-1);
        }
        return returnChar;
    }

    public  boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    public List<String> selectBuild(String buildNum,List<String> rooms){
        List<String> selectedrooms = new ArrayList<>();
        for (String room: rooms) {
            if(room.substring(0,1).equals(buildNum)){

                selectedrooms.add(room);
            }
        }
        return selectedrooms;
    }

//    public static void main(String[] args) {
////        String num  = "q";
////        System.out.println(Integer.parseInt(num));
//        List<String> selectedrooms = new ArrayList<>();
//        selectedrooms.add("2030");
//
//    }
}
