package com.ssapick.server.domain.pick.controller;

import com.ssapick.server.core.annotation.Authenticated;
import com.ssapick.server.core.annotation.CurrentUser;
import com.ssapick.server.core.response.SuccessResponse;
import com.ssapick.server.domain.pick.dto.MessageData;
import com.ssapick.server.domain.pick.service.MessageService;
import com.ssapick.server.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/message")
public class MessageController {

    private final MessageService messageService;

    @GetMapping("/send")
    @Authenticated
    public SuccessResponse<List<MessageData.Search>> searchReceiveMessage(@CurrentUser User user) {
        return SuccessResponse.of(messageService.searchSendMessage(user));
    }

    @Authenticated
    @GetMapping("/receive")
    public SuccessResponse<List<MessageData.Search>> searchSendMessage(@CurrentUser User user) {
        return SuccessResponse.of(messageService.searchReceiveMessage(user));
    }

    @Authenticated
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessResponse<Void> createMessage(
            @CurrentUser User user,
            @RequestBody MessageData.Create create
    ) {
        messageService.createMessage(user, create);
        return SuccessResponse.empty();
    }

    @Authenticated
    @DeleteMapping("/{messageId}/receive")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public SuccessResponse<Void> deleteFromMessage(
            @CurrentUser User user,
            @PathVariable Long messageId
    ) {
        messageService.deleteReceiveMessage(user, messageId);
        return SuccessResponse.empty();
    }

    @Authenticated
    @DeleteMapping("/{messageId}/send")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public SuccessResponse<Void> deleteToMessage(
            @CurrentUser User user,
            @PathVariable Long messageId
    ) {
        messageService.deleteSendMessage(user, messageId);
        return SuccessResponse.empty();
    }
}
