package com.peb.pebb.utility;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SegTec {

    private String creationDate;
    private String updateDate;
    private Long lastUpdateUser;
}
