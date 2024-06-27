package com.example.LibraryApplication.dto;

import com.example.LibraryApplication.entities.Member;
import lombok.Data;
import java.util.List;

@Data
public class MemberResponse {
    private String code;
    private List<Member> members;
    private String message;

    public MemberResponse(String code, List<Member> members, String message){
        this.code = code;
        this.members = members;
        this.message = message;
    }
}
