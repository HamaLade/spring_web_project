'use strict';

let replyIndex = {
    init: function () {
        $("#replySaveButton").on("click", () => {
            this.replySave();
        });
    },

    replySave: function () {
        let data = {
            content: $("#replyContent").val(),
        }
        let boardId = $("#boardId").val();
        console.log(data);
        console.log(boardId);
        $.ajax({
            type: "POST",
            url: `/reply/add/${boardId}`,
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
        }).done(function (fragment) {
            $("#replyCard").replaceWith(fragment);
            document.getElementById("replyContent").value='';
        }).fail(function (err) {
            alert(JSON.stringify(err));
        });
    },

    replyDelete: function (replyId) {
        let boardId = $("#boardId").val();
        $.ajax({
            type: "DELETE",
            url: `/reply/delete/${boardId}/${replyId}`,
            dataType: "text"
        }).done(function (res) {
            alert("댓글삭제가 완료되었습니다.");
            location.href = `/board/view/${boardId}`;
        }).fail(function (err) {
            alert(JSON.stringify(err));
        });
    },

}
replyIndex.init();