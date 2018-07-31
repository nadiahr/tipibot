package org.side.service;

import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.side.dao.UserToUserRequestRepository;
import org.side.entities.UserToUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import me.ramswaroop.jbot.core.slack.models.Attachment;
import me.ramswaroop.jbot.core.slack.models.RichMessage;
import slackBot.SlashCommand;

@RestController
public class UserToUserRequestService {
	@Autowired
	UserToUserRequestRepository userToUserRequestRepository;

	@RequestMapping(value = "/UserToUser", method = RequestMethod.POST)
	public UserToUserRequest saveUserToUserRequest(@RequestBody UserToUserRequest u) {
		return userToUserRequestRepository.save(u);
	}

	@RequestMapping(value = "/getUserToUser", method = RequestMethod.GET)
	public List<UserToUserRequest> listUserToUserRequest() {
		return userToUserRequestRepository.findAll();
	}
	 @Value("${BKtFurSbZcq2ynT1omxuB6EL}")
	    private String slackToken;
	@RequestMapping(value = "/slash-command", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public RichMessage onReceiveSlashCommand(@RequestParam("token") String token,
			@RequestParam("team_id") String teamId, @RequestParam("team_domain") String teamDomain,
			@RequestParam("channel_id") String channelId, @RequestParam("channel_name") String channelName,
			@RequestParam("user_id") String userId, @RequestParam("user_name") String userName,
			@RequestParam("command") String command, @RequestParam("text") String text,
			@RequestParam("response_url") String responseUrl) {
		// validate token
		if (!token.equals(slackToken)) {
			return new RichMessage("Sorry! You're not lucky enough to use our slack command.");
		}

		/** build response */
		RichMessage richMessage = new RichMessage("The is Slash Commander!");
		richMessage.setResponseType("in_channel");
		// set attachments
		Attachment[] attachments = new Attachment[1];
		attachments[0] = new Attachment();
		attachments[0].setText("I will perform all tasks for you.");
		richMessage.setAttachments(attachments);

		// For debugging purpose only
		if (logger.isDebugEnabled()) {
			try {
				logger.debug("Reply (RichMessage): {}", new ObjectMapper().writeValueAsString(richMessage));
			} catch (JsonProcessingException e) {
				logger.debug("Error parsing RichMessage: ", e);
			}
		}

		return richMessage.encodedMessage(); // don't forget to send the encoded message to Slack
	}
	 private static final Logger logger = LoggerFactory.getLogger(SlashCommand.class);

}
