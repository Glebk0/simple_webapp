function controller() {

}

function send() {
	msg = $("#msg-text").val();
	console.log(msg);
	$.ajax({
		url: "http://tomcat/SimpleWebApp-1.0-SNAPSHOT/MainServlet",
		method: "POST",
		data: {
			msg: msg
		}
	})
		.done(function (resp) {
			$(".msg").html(JSON.parse(resp).msg);
		})
		.fail;(function (resp) {
			console.log(resp);
		});
}
