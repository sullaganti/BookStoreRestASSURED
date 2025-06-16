package Utilities.POJO.response;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class BadRequest {
	private List<DetailItem> detail;
	private String message;
	@Data
	public static class Ctx{
		private String error;
	}
	@Data
	public static class DetailItem{
		private String msg;
		private List<Object> loc;
		private Ctx ctx;
		private Object input;
		private String type;
	}

}




