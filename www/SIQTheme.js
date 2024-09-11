class SIQArticleMessageTheme {
    constructor() {
        this.cardTitleColor = null;
        this.articleTitleColor = null;
        this.authorTextColor = null;
        this.backgroundColor = null;
    }
}

class SIQArticleTheme {
    constructor() {
        this.Toolbar = new SIQFAQBottomBarTheme();
    }
}

class SIQAttachmentSheetTheme {
    constructor() {
        this.backgroundColor = null;
        this.overlayColor = null;
        this.tintColor = null;
        this.separatorColor = null;
    }
}

class SIQAudioPlayerTheme {
    constructor() {
        this.thumbColor = null;
        this.playButtonBackgroundColor = null;
        this.incomingTrackColor = null;
        this.outgoingTrackColor = null;
        this.incomingButtonIconColor = null;
        this.outgoingButtonIconColor = null;
        this.outgoingDurationTextColor = null;
        this.incomingDurationTextColor = null;
    }
}

class SIQBannerColorTheme {
    constructor() {
        this.backgroundColor = null;
        this.textColor = null;
    }
}

class SIQBannerTheme {
    constructor() {
        this.successTheme = new SIQBannerColorTheme();
        this.infoTheme = new SIQBannerColorTheme();
        this.failureTheme = new SIQBannerColorTheme();
    }
}

class SIQButtonTheme {
    constructor() {
        this.selectedColor = null;
        this.normalColor = null;
    }
}

class SIQChatFormTheme {
    constructor() {
        this.backgroundColor = null;
        this.textFieldBackgroundColor = null;
        this.textFieldTextColor = null;
        this.textFieldTintColor = null;
        this.textFieldPlaceholderColor = null;
        this.textFieldTitleColor = null;
        this.textFieldRequiredIndicatorColor = null;
        this.errorColor = null;
        this.submitButtonBackgroundColor = null;
        this.submitButtonTextColor = null;
        this.campaignOptInTextColor = null;
        this.checkboxCheckedColor = null;
        this.checkboxUncheckedColor = null;
    }
}

class SIQChatInputTheme {
    constructor() {
        this.backgroundColor = null;
        this.audioRecordHintBackgroundColor = null;
        this.audioRecordHintTextColor = null;
        this.textFieldBorderColor = null;
        this.textFieldTintColor = null;
        this.textFieldBackgroundColor = null;
        this.textFieldPlaceholderColor = null;
        this.textFieldTextColor = null;
        this.attachmentButtonBackgroundColor = null;
        this.recordButtonBackgroundColor = null;
        this.recordSoundPulseBackgroundColor = null;
        this.sendButtonBackgroundColor = null;
        this.buttonDisabledBackgroundColor = null;
        this.recordTimerBackgroundColor = null;
        this.recordTimerTextColor = null;
        this.recordTimerIndicatorColor = null;
        this.recordSlideTextColor = null;
        this.recordSlideIconColor = null;
        this.recordCancelTextColor = null;
        this.moreIconColor = null;
        this.sendIconColor = null;
        this.recordIconColor = null;
        this.Reply = new SIQReplyViewTheme();
        this.Edit = new SIQReplyViewTheme();
    }
}

class SIQChatScrollButtonTheme {
    constructor() {
        this.iconColor = null;
        this.backgroundColor = null;
        this.UnreadBadge = new SIQLauncherUnreadBadgeTheme();
    }
}

class SIQChatWindowTheme {
    constructor() {
        this.backgroundColor = null;
        this.Message = new SIQMessageTheme();
        this.HandOffBanner = new SIQHandOffBannerTheme();
        this.Banner = new SIQBannerTheme();
        this.QueueBanner = new SIQQueueBannerTheme();
        this.Input = new SIQChatInputTheme();
        this.AttachmentsSheet = new SIQAttachmentSheetTheme();
        this.ScrollButton = new SIQChatScrollButtonTheme();
        this.EmailTranscript = new SIQEmailTranscriptTheme();
        this.DebugLog = new SIQLogViewTheme();
    }
}

class SIQConversationListItemTheme {
    constructor() {
        this.backgroundColor = null;
        this.timerTextColor = null;
        this.timerIconColor = null;
        this.titleTextColor = null;
        this.subtitleTextColor = null;
        this.timeTextColor = null;
        this.openBadgeBackgroundColor = null;
        this.openBadgeBorderColor = null;
        this.openBadgeTextColor = null;
        this.UnreadBadge = new SIQLauncherUnreadBadgeTheme();
    }
}

class SIQConversationTheme {
    constructor() {
        this.backgroundColor = null;
        this.ListItem = new SIQConversationListItemTheme();
    }
}

class SIQEmailTranscriptTheme {
    constructor() {
        this.textFieldTintColor = null;
    }
}

class SIQEmptyViewTheme {
    constructor() {
        this.backgroundColor = null;
        this.chatButtonBackgroundColor = null;
        this.chatButtonTitleColor = null;
        this.textColor = null;
    }
}

class SIQFAQBottomBarTheme {
    constructor() {
        this.backgroundColor = null;
        this.LikeButton = new SIQButtonTheme();
        this.DislikeButton = new SIQButtonTheme();
    }
}

class SIQFAQListItemTheme {
    constructor() {
        this.backgroundColor = null;
        this.titleTextColor = null;
        this.subtitleTextColor = null;
        this.subtitlePartitionColor = null;
        this.likePendingColor = null;
        this.likedColor = null;
        this.separatorColor = null;
    }
}

class SIQFAQTheme {
    constructor() {
        this.headerTextColor = null;
        this.ListItem = new SIQFAQListItemTheme();
        this.Article = new SIQArticleTheme();
    }
}

class SIQFeedbackTheme {
    constructor() {
        this.backgroundColor = null;
        this.primaryTextColor = null;
        this.secondaryTextColor = null;
        this.skipButtonTextColor = null;
        this.submitButtonTextColor = null;
        this.submitButtonBackgroundColor = null;
        this.feedbackTextFieldTintColor = null;
        this.feedbackPlaceholderTextColor = null;
    }
}

class SIQFileMessageTheme {
    constructor() {
        this.incomingTitleColor = null;
        this.incomingSubTitleColor = null;
        this.outgoingTitleColor = null;
        this.outgoingSubTitleColor = null;
        this.incomingFileViewBackgroundColor = null;
        this.outgoingFileViewBackgroundColor = null;
        this.incomingCommentBackgoundColor = null;
        this.outgoingCommentBackgoundColor = null;
    }
}

class SIQHandOffBannerTheme {
    constructor() {
        this.backgroundColor = null;
        this.textColor = null;
        this.buttonTitleColor = null;
    }
}

class SIQInAppNotificationTheme {
    constructor() {
        this.titleColor = null;
        this.subtitleColor = null;
        this.backgroundColor = null;
        this.imageBackgroundColor = null;
        this.cornerRadius = null; // Int
        this.imageCornerRadius = null; // Int
    }
}

class SIQInfoMessageTheme {
    constructor() {
        this.textColor = null;
        this.lineColor = null;
    }
}

class SIQInputCardTheme {
    constructor() {
        this.titleColor = null;
        this.textFieldTextColor = null;
        this.textFieldBackgroundColor = null;
        this.textFieldPlaceholderColor = null;
        this.sendButtonBackgroundColor = null;
        this.sendButtonIconColor = null;
        this.separatorColor = null;
    }
}

class SIQLauncherTheme {
    constructor() {
        this.backgroundColor = null;
        this.iconColor = null;
        this.UnreadBadge = new SIQLauncherUnreadBadgeTheme();
    }
}

class SIQLauncherUnreadBadgeTheme {
    constructor() {
        this.backgroundColor = null;
        this.textColor = null;
        this.borderColor = null;
        this.borderWidth = null;
    }
}

class SIQLogViewTheme {
    constructor() {
        this.backgroundColor = null;
        this.titleColor = null;
        this.textViewColor = null;
        this.textViewBackgroundColor = null;
        this.sendTitleColor = null;
        this.sendBackgroundColor = null;
        this.cancelTitleColor = null;
    }
}

class SIQMessageCommonTheme {
    constructor() {
        this.botTypingIndicatorStyle = null;
        this.messageSenderNameColor = null;
        this.outgoingBackgroundColor = null;
        this.outgoingTextColor = null;
        this.outgoingBorderColor = null;
        this.outgoingTimeTextColor = null;
        this.outgoingTimeIconColor = null;
        this.incomingBackgroundColor = null;
        this.incomingTextColor = null;
        this.incomingBorderColor = null;
        this.incomingTimeTextColor = null;
        this.incomingTimeIconColor = null;
        this.incomingTextTimeColor = null;
        this.outgoingTextTimeColor = null;
        this.messageStatusIconColor = null;
        this.incomingMessageEditedTagColor = null;
        this.outgoingMessageEditedTagColor = null;
        this.incomingMessageTimeStampColor = null;
        this.outgoingMessageTimeStampColor = null;
        this.incomingDeletedMessageColor = null;
        this.outgoingDeletedMessageColor = null;
        this.deletingMessageTitleColor = null;
        this.deliveryStatusIconColor = null;
        this.repliedMessageHighLightColor = null;
        this.incomingMessageReplyIconColor = null;
        this.outgoingMessageReplyIconColor = null;
        this.incomingProgressButton = new SIQProgressButtonTheme();
        this.outgoingProgressButton = new SIQProgressButtonTheme();
        this.outgoingRepliedMessage = new SIQReplyViewTheme();
        this.incomingRepliedMessage = new SIQReplyViewTheme();
    }
}

class SIQMessageTheme {
    constructor() {
        this.Common = new SIQMessageCommonTheme();
        this.Suggestion = new SIQSuggestionTheme();
        this.SkipActionButton = new SIQSkipActionButtonTheme();
        this.AudioPlayer = new SIQAudioPlayerTheme();
        this.Selection = new SIQSelectionComponentTheme();
        this.InputCard = new SIQInputCardTheme();
        this.Slider = new SIQSliderCardTheme();
        this.InfoMessage = new SIQInfoMessageTheme();
        this.Article = new SIQArticleMessageTheme();
        this.File = new SIQFileMessageTheme();
    }
}

class SIQNavigationTheme {
    constructor() {
        this.backgroundColor = null;
        this.titleColor = null;
        this.tintColor = null;
    }
}

class SIQNoNetworkBannerTheme {
    constructor() {
        this.backgroundColor = null;
        this.textColor = null;
        this.loaderColor = null;
    }
}

class SIQOfflineBannerTheme {
    constructor() {
        this.textColor = null;
        this.backgroundColor = null;
    }
}

class SIQProgressButtonTheme {
    constructor() {
        this.backgroundColor = null;
        this.tintColor = null;
    }
}

class SIQQueueBannerTheme {
    constructor() {
        this.backgroundColor = null;
        this.titleColor = null;
        this.subtitleColor = null;
        this.subtitleTimeColor = null;
        this.positionTextColor = null;
        this.positionSubtitleColor = null;
        this.positionContainerBackgroundColor = null;
    }
}

class SIQReplyViewTheme {
    constructor() {
        this.backgroundColor = null;
        this.titleColor = null;
        this.subtitleColor = null;
        this.verticalLine = null;
        this.messageTypeIconColor = null;
        this.closeButton = null;
    }
}

class SIQSelectionComponentTheme {
    constructor() {
        this.textColor = null;
        this.accessoryColor = null;
        this.backgroundColor = null;
        this.selectionBackgroundColor = null;
        this.buttonTextColor = null;
        this.buttonBackgroundColor = null;
        this.linkTextColor = null;
    }
}

class SIQSkipActionButtonTheme {
    constructor() {
        this.textColor = null;
        this.borderColor = null;
        this.backgroundColor = null;
        this.cornerRadius = null;
    }
}

class SIQSliderCardTheme {
    constructor() {
        this.thumbBorderColor = null;
        this.selectedTrackColor = null;
        this.selectedValueTextColor = null;
        this.unSelectedTrackColor = null;
        this.thumbBackgroundColor = null;
        this.minRangeTextColor = null;
        this.maxRangeTextColor = null;
    }
}

class SIQSuggestionTheme {
    constructor() {
        this.textColor = null;
        this.borderColor = null;
        this.backgroundColor = null;
        this.cornerRadius = null;
        this.displayStyle = null;
    }
}

class SIQTabBarTheme {
    constructor() {
        this.backgroundColor = null;
        this.activeTabColor = null;
        this.inactiveTabColor = null;
    }
}

class SIQTheme {
    constructor() {
        this.themeColor = null; 
        this.Launcher = new SIQLauncherTheme();
        this.TabBar = new SIQTabBarTheme();
        this.Navigation = new SIQNavigationTheme();
        this.EmptyView = new SIQEmptyViewTheme();
        this.OfflineBanner = new SIQOfflineBannerTheme();
        this.NetworkWaitingBanner = new SIQNoNetworkBannerTheme();
        this.Conversation = new SIQConversationTheme();
        this.FAQ = new SIQFAQTheme();
        this.Chat = new SIQChatWindowTheme();
        this.Form = new SIQChatFormTheme();
        this.Feedback = new SIQFeedbackTheme();
        this.InAppNotification = new SIQInAppNotificationTheme();
    }
}

module.exports = SIQTheme;