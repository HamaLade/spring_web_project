'use strict';

function userDelete() {

    if (!$("#password").val().replace(/(^\s*)|(\s*$)/gi, "")) {
        alert("계정 삭제를 진행하려면 계정의 비밀번호를 입력해주세요");
        document.getElementById("password").focus();
    } else {
        let data = {
            password: $("#password").val(),
            username: $("#username").val(),
        }
        $.ajax({
            type: "DELETE",
            url: `/user/delete`,
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
        }).done(function (fragment) {
            if (fragment === 'true') {
                alert("계정 삭제가 완료되었습니다");
                location.href = '/logout';
            } else {
                alert("입력하신 비밀번호가 회원비밀번호와 일치하지 않습니다");
                document.getElementById("password").focus();
            }
        }).fail(function (err) {
            alert("오류로 인하여 계정삭제에 실패했습니다");
        });
    }
}