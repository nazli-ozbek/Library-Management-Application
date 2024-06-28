package com.example.LibraryApplication.controllers;

import com.example.LibraryApplication.dto.BookResponse;
import com.example.LibraryApplication.dto.MemberRequest;
import com.example.LibraryApplication.dto.MemberResponse;
import com.example.LibraryApplication.entities.Member;
import com.example.LibraryApplication.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/members")
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    @GetMapping("/getall")
    public MemberResponse getAllMembers(){
        try {
            return new MemberResponse("200",memberService.getAllMembers(),"All books");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/getmembers")
    public MemberResponse getMembers(@RequestBody MemberRequest memberRequest) {
        try {
            return memberService.getMembersService(memberRequest);
        } catch (Exception e) {
            return new MemberResponse("500",null, "Member not found!");
        }
    }

    @PostMapping("/create")
    public MemberResponse createMember(@RequestBody MemberRequest memberRequest) {
        try {
            Member member = new Member(memberRequest.getName(),memberRequest.getSurname(), memberRequest.getPhoneNumber());
            Member created = memberService.createMember(member);
            List<Member> list = new ArrayList<>();
            list.add(created);
            return new MemberResponse("200",list, "Creation successful");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public MemberResponse deleteMember(@RequestBody MemberRequest memberRequest) {
        try {
            List<Member> list = new ArrayList<>();
            list.add(memberService.getMemberByID(memberRequest.getId()));
            memberService.deleteMember(memberRequest.getId());
            return new MemberResponse("200",list, "Deletion successful");
        } catch (Exception e) {
            return new MemberResponse("500",null, "Member not found!");
        }
    }

    @PutMapping("/update")
    public MemberResponse updateMember(@RequestBody MemberRequest memberRequest) {
        try {
            Member newMember = new Member(memberRequest.getName(),memberRequest.getSurname(), memberRequest.getPhoneNumber());
            Member updated = memberService.updateMember(memberRequest.getId(),newMember);
            List<Member> list = new ArrayList<>();
            list.add(updated);
            return new MemberResponse("200",list, "Update successful");
        } catch (Exception e) {
            return new MemberResponse("500",null, "Member not found!");
        }
    }
}
