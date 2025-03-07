package com.bytedance.android.lesson.restapi.solution.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Xavier.S
 * @date 2019.01.20 14:18
 */
public class Feed {

    // TODO-C2 (1) Implement your Feed Bean here according to the response json
    /*
        student_id
        user_name
        image_url
        video_url
         */
    @SerializedName("student_id") private String student_id;
    @SerializedName("user_name") private String user_name;
    @SerializedName("image_url") private String image_url;
    @SerializedName("video_url") private String video_url;
    public String getStudent_id(){return student_id;}
    public String getUser_name(){return user_name;}
    public String getImage_url(){return image_url;}
    public String getVideo_url(){return video_url;}
    public void setStudent_id(){this.student_id = student_id;}
    public void setUser_name(){this.user_name = user_name;}
    public void setImage_url(){this.image_url = image_url;}
    public void setVideo_url(){this.video_url = video_url;}

    @Override public String toString(){
        return "Value{" +
                "student_id=" + student_id +
                ",user_name=" + user_name +
                ",image_url=" + image_url +
                ",video_url=" + video_url +
                "}";
    }
}
