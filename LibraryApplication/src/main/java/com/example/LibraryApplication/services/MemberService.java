package com.example.LibraryApplication.services;

import com.example.LibraryApplication.dto.MemberRequest;
import com.example.LibraryApplication.dto.MemberResponse;
import com.example.LibraryApplication.entities.Member;
import com.example.LibraryApplication.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    public List<Member> getAllMembers(){
        return memberRepository.findAll();
    }

    public List<Member> getMembers(String name, String surname, String phoneNumber) {
        List<Member> members = memberRepository.findAll();
        return members.stream()
                .filter(member -> (name == null || member.getName().equals(name)) &&
                        (member == null || member.getSurname().equals(surname)) &&
                        (member == null || member.getPhoneNumber().equals(phoneNumber)))
                .collect(Collectors.toList());
    }


    public Member getMemberByID(long id){
        return memberRepository.findById(id).get();
    }

    public Member createMember(Member member){
        return memberRepository.save(member);
    }

    public Member updateMember(long id, Member newMember){
        Optional <Member> oldMemberOptional = memberRepository.findById(id);
        if(oldMemberOptional.isPresent()){
            Member oldMember = oldMemberOptional.get();
            if(!ObjectUtils.isEmpty(newMember.getName())){
            oldMember.setName(newMember.getName());}
            if(!ObjectUtils.isEmpty(newMember.getSurname())){
            oldMember.setSurname(newMember.getSurname());}
            if(!ObjectUtils.isEmpty(newMember.getPhoneNumber())){
            oldMember.setPhoneNumber(newMember.getPhoneNumber());}

            return memberRepository.save(oldMember);
        }
        else{
            throw new RuntimeException("Member not found!");
        }
    }

    public void deleteMember(long id){
        if(memberRepository.existsById(id)){
            memberRepository.deleteById(id);
        }
        else{
            throw new RuntimeException("Member not found!");
        }

    }

    public MemberResponse getMembersService(MemberRequest memberRequest){
        List<Member> resultMembers = getMembers(memberRequest.getName(),memberRequest.getSurname(),memberRequest.getPhoneNumber());
        if(resultMembers.isEmpty()){
            return new MemberResponse("200", null, "Members cannot be found!");
        }
        return new MemberResponse("200", resultMembers, "List of the members");
    }

}
