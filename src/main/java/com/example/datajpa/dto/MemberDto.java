package com.example.datajpa.dto;

import lombok.Data;

@Data // entity에는 getter/setter가 모두 포함될 수 있으므로 사용 지양
public class MemberDto {
    private Long id;
    private String username;
    private String teamName;

    public MemberDto(Long id, String username, String teamName) {
        this.id = id;
        this.username = username;
        this.teamName = teamName;
    }
}
