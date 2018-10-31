package com.swq.WeiXinBasePackage.domain;

import com.swq.WeiXinBasePackage.enums.RequestMessageEnum;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.*;

/**
 * 
 * @description: 用户请求消息类
 * @author:
 */
public class RequestMessage {
	private String toUser;// 开发者微信号
	private String fromUser;// OpenID
	private long createTime;// 消息创建时间 （整型）
	private RequestMessageEnum msgType;// 消息类型（文本、图文等）
	private String msgId;// 消息id，64位整型
	private String content;// 消息内容
	private String imageUrl;// 图片地址（限图文消息）
	private String format;// 语音格式，如amr，speex等
	private String recognition;// 语音识别结果，使用UTF8编码
	private String mediaId;// 媒体id，可以调用多媒体文件下载接口拉取数据。
	private String thumbMediaId;// 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。
	private String locationX;// 地理位置维度
	private String locationY;// 地理位置经度
	private String locationLabel;// 地理位置信息
	private String locationScale;// 地图缩放大小
	private String linkTitle;// 消息标题
	private String linkDescription;// 消息描述
	private String linkUrl;// 消息链接
	private String event;
	private String eventKey;
	private String ticket;
	private String latitude;
	private String longitude;
	private String precision;

	@SuppressWarnings("unused")
	private Log logger = LogFactory.getLog(RequestMessage.class);

	/**
	 * 
	 * @param xml 通过传输的xml构建对象
	 * @throws DocumentException
	 */
	public RequestMessage(String xml) throws DocumentException {
		Document document = DocumentHelper.parseText(xml);
		Element rootElement = document.getRootElement();
		Node fromUserNode = rootElement.selectSingleNode("FromUserName");
		Node toUserNode = rootElement.selectSingleNode("ToUserName");
		Node createTimeNode = rootElement.selectSingleNode("CreateTime");
		Node msgTypeNode = rootElement.selectSingleNode("MsgType");
		Node msgIdNode = rootElement.selectSingleNode("MsgId");
		fromUser = fromUserNode.getStringValue();
		toUser = toUserNode.getStringValue();
		createTime = Long.parseLong(createTimeNode.getText());
		msgType = RequestMessageEnum.fromString(msgTypeNode.getStringValue());

		// if (!msgType.equals(EVENT_MESSAGE)) {
		if (msgType != RequestMessageEnum.EVENT_MESSAGE) {
			msgId = msgIdNode.getStringValue();
		}

		switch (msgType) {
		case TEXT_MESSAGE:
			content = rootElement.selectSingleNode("Content").getStringValue();
			break;
		case IMAGE_MESSAGE:
			imageUrl = rootElement.selectSingleNode("PicUrl").getStringValue();
			mediaId = rootElement.selectSingleNode("MediaId").getStringValue();
			break;
		case VOICE_MESSAGE:
			format = rootElement.selectSingleNode("Format").getStringValue();
			mediaId = rootElement.selectSingleNode("MediaId").getStringValue();
			Node recognitionNode = rootElement.selectSingleNode("Recognition");
			if (recognitionNode != null) {
				recognition = recognitionNode.getStringValue();
			} else {
				recognition = null;
			}
			break;
		case VIDEO_MESSAGE:
			mediaId = rootElement.selectSingleNode("MediaId").getStringValue();
			thumbMediaId = rootElement.selectSingleNode("ThumbMediaId").getStringValue();
			break;
		case SHORTVIDEO_MESSAGE:
			mediaId = rootElement.selectSingleNode("MediaId").getStringValue();
			thumbMediaId = rootElement.selectSingleNode("ThumbMediaId").getStringValue();
			break;
		case LOCATION_MESSAGE:
			locationX = rootElement.selectSingleNode("Location_X").getStringValue();
			locationY = rootElement.selectSingleNode("Location_Y").getStringValue();
			locationLabel = rootElement.selectSingleNode("Label").getStringValue();
			locationScale = rootElement.selectSingleNode("Scale").getStringValue();
			break;
		case LINK_MESSAGE:
			linkTitle = rootElement.selectSingleNode("Title").getStringValue();
			linkDescription = rootElement.selectSingleNode("Description").getStringValue();
			linkUrl = rootElement.selectSingleNode("Url").getStringValue();
			break;
		case EVENT_MESSAGE:
			event = rootElement.selectSingleNode("Event").getStringValue();
			Node eventKeyNode = rootElement.selectSingleNode("EventKey");
			Node ticketNode = rootElement.selectSingleNode("Ticket");
			Node latitudeNode = rootElement.selectSingleNode("Latitude");
			Node longitudeNode = rootElement.selectSingleNode("Longitude");
			Node precisionNode = rootElement.selectSingleNode("Precision");
			if (eventKeyNode != null) {
				eventKey = eventKeyNode.getStringValue();
			} else {
				eventKey = null;
			}
			if (ticketNode != null) {
				ticket = ticketNode.getStringValue();
			} else {
				ticket = null;
			}
			if (latitudeNode != null) {
				latitude = latitudeNode.getStringValue();
			} else {
				latitude = null;
			}
			if (longitudeNode != null) {
				longitude = longitudeNode.getStringValue();
			} else {
				longitude = null;
			}
			if (precisionNode != null) {
				precision = precisionNode.getStringValue();
			} else {
				precision = null;
			}
			break;
		default:
			break;
		}
	}

	public String getToUser() {
		return toUser;
	}

	public String getFromUser() {
		return fromUser;
	}

	public long getCreateTime() {
		return createTime;
	}

	public RequestMessageEnum getMsgType() {
		return msgType;
	}

	public String getMsgId() {
		return msgId;
	}

	public String getContent() {
		return content;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public String getLocationX() {
		return locationX;
	}

	public String getLocationY() {
		return locationY;
	}

	public String getLocationLabel() {
		return locationLabel;
	}

	public String getLocationScale() {
		return locationScale;
	}

	public String getLinkTitle() {
		return linkTitle;
	}

	public String getLinkDescription() {
		return linkDescription;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public String getEvent() {
		return event;
	}

	public String getEventKey() {
		return eventKey;
	}

	public String getFormat() {
		return format;
	}

	public String getMediaId() {
		return mediaId;
	}

	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public String getPrecision() {
		return precision;
	}

	public String getLongitude() {
		return longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public String getTicket() {
		return ticket;
	}

	public String getRecognition() {
		return recognition;
	}
}