
$(function() {
    validateRule();
    $('.imgcode').click(function() {
        var url = ctx + "captcha/captchaImage?type=" + captchaType + "&s=" + Math.random();
        $(".imgcode").attr("src", url);
    });
});

$.validator.setDefaults({
    submitHandler: function() {
        register();
    }
});

function register() {
    $.modal.loading($("#btnSubmit").data("loading"));
    var username = $.common.trim($("input[name='username']").val());
    var password = $.common.trim($("input[name='password']").val());
    var validateCode = $("input[name='validateCode']").val();
    $.ajax({
        type: "post",
        url: ctx + "register",
        data: {
            "loginName": username,
            "password": password,
            "validateCode": validateCode
        },
        success: function(r) {
            if (r.code == web_status.SUCCESS) {
                layer.alert("<font color='red'>おめでとうございます、アカウント " + username + " の登録が完了しました。</font>", {
                    icon: 1,
                    title: "システムメッセージ"
                },
                function(index) {
                    //关闭弹窗
                    layer.close(index);
                    location.href = ctx + 'login';
                });
            } else {
                $.modal.closeLoading();
                $('.imgcode').click();
                $(".code").val("");
                $.modal.msg(r.msg);
            }
        }
    });
}

function validateRule() {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#registerForm").validate({
        rules: {
            username: {
                required: true,
                minlength: 2
            },
            password: {
                required: true,
                minlength: 5
            },
            confirmPassword: {
                required: true,
                equalTo: "[name='password']"
            }
        },
        messages: {
            username: {
                required: icon + "ユーザー名を入力してください。",
                minlength: icon + "ユーザー名は2文字以上である必要があります。"
            },
            password: {
                required: icon + "パスワードを入力してください。",
                minlength: icon + "パスワードは5文字以上である必要があります。",
            },
            confirmPassword: {
                required: icon + "もう一度パスワードを入力してください。",
                equalTo: icon + "2回のパスワード入力が一致しません。"
            }
        }
    })
}
