package com.messenger.groupservice.dto.openfeign_dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StorageServiceResponse {

    private String file_uri;
}
