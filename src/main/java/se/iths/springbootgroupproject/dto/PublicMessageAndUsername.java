package se.iths.springbootgroupproject.dto;

import java.io.Serializable;
import java.time.LocalDate;

public record PublicMessageAndUsername(LocalDate date,String title,String messageBody, String userUserName) implements Serializable {
}
