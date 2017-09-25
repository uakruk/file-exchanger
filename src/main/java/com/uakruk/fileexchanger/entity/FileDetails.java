package com.uakruk.fileexchanger.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Calendar;

/**
 * fileexchanger class
 *
 * @author Yaroslav Kruk on 9/24/17.
 * e-mail: yakruck@gmail.com
 * GitHub: https://github.com/uakruk
 * @version 1.0
 * @since 1.8
 */

@Entity
@Table(name = "file_details")
@Data
@ToString
public class FileDetails {

    @Id
    @Column(name = "file_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_token", unique = true)
    @NotNull
    private String fileToken;

    @Column
    private String description;

    @Column(name = "original_name")
    private String originalName;

    @Column(name = "expired_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar expired;

}
