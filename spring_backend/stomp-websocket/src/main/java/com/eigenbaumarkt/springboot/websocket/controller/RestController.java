package com.eigenbaumarkt.springboot.websocket.controller;


import com.eigenbaumarkt.springboot.websocket.model.Greeting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.core.MessagePostProcessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.HtmlUtils;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {

    @Autowired
    private SimpMessagingTemplate template;

    private static final Logger logger = LoggerFactory.getLogger(RestController.class);

    // Aufruf von 'localhost:8080/api/changestate' löst unter 'localhost:4200' eine "State changed"-message aus
    // siehe: https://youtu.be/r2tPEfVgsIE?t=1280
    @GetMapping("/changestate")
    public void greeting() throws Exception {
        this.template.convertAndSend("/topic/greetings", new Greeting("Hello " + HtmlUtils.htmlEscape("Jochen Gebsattel - State changed") + "!"),
                new MessagePostProcessor() {
                    @Override
                    public Message<?> postProcessMessage(Message<?> message) {
                        byte[] byteStr = message.getPayload().toString().getBytes();
                        String msg = new String(byteStr);
                        return message;
                    }
                }
        );
    }
}
