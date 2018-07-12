var socket = new SockJS('/clientConnectThis');
var stompClient = Stomp.over(socket);
stompClient.connect({},
    function connectCallback(frame) {   // success
        connectResult("Connect Success");
        stompClient.subscribe('/app/firstConnection', function (response) {
            var returnData = JSON.parse(response.body);
            receiveText("/app/firstConnection,Test Receive:" + returnData.greeting);
        });
    },
    function errorCallBack(error) {     // failed
        connectResult("Connect Break");
    }
);

//发送消息
function sendTopic() {
    var topic = $('#topic').val();
    var message = $('#message').val();
    var name = $('#name').val();
    if(topic == "")
        topic = "Topic";
    if(message == "")
        message = "Greeting";
    if(name == "")
        name = "Name";
    var messageJson = JSON.stringify({"topic":topic,"greeting": message,"name":name});
    stompClient.send("/app/sendMessageTopic", {}, messageJson);
    sendText("T-You Send:" + messageJson);
}
function sendQueue() {
    var topic = $('#topic').val();
    var message = $('#message').val();
    var name = $('#name').val();
    if(topic == "")
        topic = "Topic";
    if(message == "")
        message = "Greeting";
    if(name == "")
        name = "Name";
    var messageJson = JSON.stringify({"topic":topic,"greeting": message,"name":name});
    stompClient.send("/app/sendMessageQueue", {}, messageJson);
    sendText("Q-You Send:" + messageJson);
}

//订阅消息
function subscribeTopic(t) {
    $(t).css({
        "backgroundColor": "#000"
    });
    stompClient.subscribe('/topic/webSocketTopic', function (response) {
        var returnData = JSON.parse(response.body);
        receiveText("A-You Receive:(name=" + returnData.name + ",greeting=" + returnData.greeting + ",topic=" + returnData.topic + ")")
    });
    stompClient.subscribe('/topic/webSocketTopic', function (response) {
        var returnData = JSON.parse(response.body);
        receiveText("B-You Receive:(name=" + returnData.name + ",greeting=" + returnData.greeting + ",topic=" + returnData.topic + ")")
    });
}

//订阅消息
function subscribeQueue(t) {
    $(t).css({
        "backgroundColor": "#000"
    });
    stompClient.subscribe('/user/queue/webSocketQueue', function (response) {
        var returnData = JSON.parse(response.body);
        receiveText("C-You Receive:(name=" + returnData.name + ",greeting=" + returnData.greeting + ",topic=" + returnData.topic + ")")
    });
    stompClient.subscribe('/user/queue/webSocketQueue', function (response) {
        var returnData = JSON.parse(response.body);
        receiveText("D-You Receive:(name=" + returnData.name + ",greeting=" + returnData.greeting + ",topic=" + returnData.topic + ")")
    });
}

function subscribePoint(t) {
    $(t).css({
        "backgroundColor": "#000"
    });
    // /user/{userId}/**
    stompClient.subscribe('/user/110/queue/pushInfo', function (response) {
        var returnData = JSON.parse(response.body);
        receiveText("/user/110/queue/pushInfo,Receive:" + returnData.greeting);
    });
}

function sendKafka() {
    var topic = $('#topic').val();
    var message = $('#message').val();
    var name = $('#name').val();
    if(topic == "")
        topic = "Topic";
    if(message == "")
        message = "Greeting";
    if(name == "")
        name = "Name";
    var messageJson = JSON.stringify({"topic":topic,"greeting": message,"name":name});
    stompClient.send("/app/sendKafka", {}, messageJson);
    sendText("Kafka Send:" + messageJson);
}

function kafkaReceive(t) {
    $(t).css({
        "backgroundColor": "#000"
    });
    stompClient.subscribe('/user/kafka-user-id/queue/kafkaMsg', function (response) {
        var returnData = JSON.parse(response.body);
        receiveText("Kafka Receive:(name=" + returnData.name + ",greeting=" + returnData.greeting + ",topic=" + returnData.topic + ")")
    });
}


function sendText(v) {
    $('.container .right').append($('<div class="common-message send-text">'+ v +'</div>'));
}

function receiveText(v) {
    $('.container .right').append($('<div class="common-message receive-text">'+ v +'</div>'));
}

function connectResult(v) {
    $('.container').append($('<div class="connect-text">'+ v +'</div>'))
}