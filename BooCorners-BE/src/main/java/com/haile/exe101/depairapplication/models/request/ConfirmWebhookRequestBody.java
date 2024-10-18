package com.haile.exe101.depairapplication.models.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ConfirmWebhookRequestBody {
    private String webhookUrl;
}
