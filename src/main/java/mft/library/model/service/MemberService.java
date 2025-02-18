package mft.library.model.service;

import mft.library.model.entity.Member;
import mft.library.model.repository.MemberRepository;

import java.util.List;

public class MemberService {
    public static void save(Member member) throws Exception {
        if (!(member.getBirthDate().getYear() >= 1980 && member.getBirthDate().getYear() <= 2020)) {
            throw new Exception("Invalid Birth Year");
        }
        try (MemberRepository memberRepository = new MemberRepository()) {
            memberRepository.save(member);
        }
    }

    public static void edit(Member member) throws Exception {
        findById(member.getId());
        if (!(member.getBirthDate().getYear() >= 1980 && member.getBirthDate().getYear() <= 2020)) {
            throw new Exception("Invalid Birth Year");
        }
        try (MemberRepository memberRepository = new MemberRepository()) {
            memberRepository.edit(member);
        }
    }

    public static void remove(int id) throws Exception {
        findById(id);
        try (MemberRepository memberRepository = new MemberRepository()) {
            memberRepository.remove(id);
        }
    }

    public static List<Member> findAll() throws Exception {
        try (MemberRepository memberRepository = new MemberRepository()) {
            List<Member> memberList = memberRepository.findAll();
            if (memberList.isEmpty()) {
                throw new Exception("Member not found");
            }
            return memberList;
        }
    }

    public static Member findById(int id) throws Exception {
        try (MemberRepository memberRepository = new MemberRepository()) {
            Member member = memberRepository.findById(id);
            if (member == null) {
                throw new Exception("Member not found");
            }
            return member;
        }
    }

    public static List<Member> findByNameAndFamily(String name, String family) throws Exception {
        try (MemberRepository memberRepository = new MemberRepository()) {
            List<Member> memberList = memberRepository.findByNameAndFamily(name, family);
            if (memberList.isEmpty()) {
                throw new Exception("Member not found");
            }
            return memberList;
        }
    }
}
