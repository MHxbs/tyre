package team.redrock.tyre.service;



import team.redrock.tyre.domain.response.EmptyResponse;

import java.util.List;

public interface EmptyRoomService {
    public EmptyResponse getRoom(String weekdayNum, String sectionNum, String week, String buildNum);
    public List<String> selectEmpty(String weekdayNum, String sectionNum, String week, EmptyResponse emptyResponse);
//    public void saveRoom();
    public List<String> getRooms(String weekdayNum, String sectionNum, String week, String buildName);
    public void deleteCahce();
}
