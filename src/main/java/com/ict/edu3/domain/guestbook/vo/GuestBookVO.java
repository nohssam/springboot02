package com.ict.edu3.domain.guestbook.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuestBookVO {
    private String gb_idx, gb_name, gb_subject, gb_content, gb_email, gb_pw, gb_regdate;
}
