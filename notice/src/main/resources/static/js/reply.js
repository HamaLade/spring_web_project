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
        if (!data.content.replace(/(^\s*)|(\s*$)/gi, "")) {
            alert("댓글 내용을 작성해야합니다");
            document.getElementById("replyContent").focus();
        }
        else {
            let boardId = $("#boardId").val();
            $.ajax({
                type: "POST",
                url: `/reply/add/${boardId}`,
                data: JSON.stringify(data),
                contentType: "application/json; charset=utf-8",
            }).done(function (fragment) {
                $("#replyZone").replaceWith(fragment);
                document.getElementById("replyContent").value = '';
            }).fail(function (err) {
                alert(JSON.stringify(err));
            });
        }
    },

    replyDelete: function (replyId) {
        let boardId = $("#boardId").val();
        let replyPage = $("#replyPage").val();
        $.ajax({
            type: "DELETE",
            url: `/reply/delete/${boardId}/${replyPage}/${replyId}`,
            dataType: "text",
            contentType: "application/json; charset=utf-8",
        }).done(function (fragment) {
            $("#replyZone").replaceWith(fragment);
        }).fail(function (err) {
            alert(JSON.stringify(err));
        });
    },

    replyPaging: function (replyPage) {
        let boardId = $("#boardId").val();
        $.ajax({
            type: "GET",
            url: `/reply/page/${boardId}/${replyPage}`,
            dataType: "text",
            contentType: "application/json; charset=utf-8",
        }).done(function (fragment) {
            $("#replyZone").replaceWith(fragment);
        }).fail(function (err) {
            alert(JSON.stringify(err));
        });
    },

}
replyIndex.init();