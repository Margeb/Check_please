package pl.margeb.check_please.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Message {
    private String title;
    private String content;

    public static Message info(String msg) {
        return new Message("Info", msg);
    }

    public static Message error(String msg){
        return new Message("Error", msg);
    }
}
