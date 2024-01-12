/*this is basic form validation using for validation person's basic information author:Clara Guo data:2017/07/20*/
$(document).ready(function(){
	$.validator.setDefaults({       
		  submitHandler: function(form) {    
		 		form.submit();    
		}       
	});  
	//電話番号および身分証明書の正規表現を結合：(^\d{15}$)|(^\d{17}([0-9]|X)$)
	jQuery.validator.addMethod("isPhone",function(value,element){
		var length = value.length;
		var phone=/^1[3-9]\d{9}$/;
		return this.optional(element)||(length == 11 && phone.test(value));
	},"正しい11桁の電話番号を入力してください");
	//電話番号の検証
	jQuery.validator.addMethod("isTel",function(value,element){
		var tel = /^(0\d{2,3}-)?\d{7,8}$/g;//エリアコード3,4桁, 番号7,8桁
		return this.optional(element) || (tel.test(value));
	},"正しい電話番号を入力してください");
	//氏名の検証
	jQuery.validator.addMethod("isName",function(value,element){
		var name=/^[\u4e00-\u9fa5]{2,6}$/;
		return this.optional(element) || (name.test(value));
	},"氏前は漢字のみ使用でき、2〜6文字の長さである必要があります");
	//ユーザー名の検証
	jQuery.validator.addMethod("isUserName",function(value,element){
		var userName=/^[a-zA-Z0-9]{2,13}$/;
		return this.optional(element) || (userName).test(value);
	},'数字またはアルファベットを入力してください。特殊文字は含まれていません');
	
	//身分証明書の検証
	jQuery.validator.addMethod("isIdentity",function(value,element){
		var id= /^(\d{15}$|^\d{18}$|^\d{17}(\d|X))$/;
		return this.optional(element) || (id.test(value));
	},"正しい15桁または18桁の身分証明書番号を入力してください。Xで終わる場合は大文字にしてください。");
	//二代身份証明書（18桁）の検証
	jQuery.validator.addMethod("isIdentity18",function(value,element){
		var id= /^(^\d{17}(\d|X))$/;
		return this.optional(element) || (id.test(value));
	},"正しい18桁の身分証明書番号を入力してください。Xで終わる場合は大文字にしてください。");
	//生年月日の検証
	jQuery.validator.addMethod("isBirth",function(value,element){
		var birth = /^(19|20)\d{2}-(1[0-2]|0?[1-9])-(0?[1-9]|[1-2][0-9]|3[0-1])$/;
		return this.optional(element) || (birth).test(value);
	},"生年月日の形式は、2000-01-01のように入力してください。");
	//IPアドレスの検証
	jQuery.validator.addMethod("isIp",function(value,element){
		var ip = /^(?:(?:2[0-4][0-9]\.)|(?:25[0-5]\.)|(?:1[0-9][0-9]\.)|(?:[1-9][0-9]\.)|(?:[0-9]\.)){3}(?:(?:2[0-4][0-9])|(?:25[0-5])|(?:1[0-9][0-9])|(?:[1-9][0-9])|(?:[0-9]))$/;
		return this.optional(element) || (ip).test(value);
	},"IPアドレスの形式は、127.0.0.1のように入力してください。");
	jQuery.validator.addMethod("notEqual", function(value, element, param) {
        return value != param;
    }, $.validator.format("入力値は{0}であってはいけません。"));
	jQuery.validator.addMethod("gt", function(value, element, param) {
        return value > param;
    }, $.validator.format("入力値は{0}より大きくなければなりません。"));
	//校验新密码和确认密码是否相同
	jQuery.validator.addMethod("isdiff",function(){
		var p1=$("#pwdOld").val();
		var p2=$("#pwdNew").val();
		if(p1==p2){
			return false;
		}else{
			 return true;
		}
		});
	//新しいパスワードと確認用パスワードが同じであるかどうかを検証する
	jQuery.validator.addMethod("issame",function(){
		var p3=$("#confirm_password").val();
		var p4=$("#pwdNew").val();
		if(p3==p4){
			return true;
		}else{
			 return false;
		}
		});
	//基本情報フォームを検証する
	$("#basicInfoForm").validate({
		errorElement:'span',
		errorClass:'help-block error-mes',
		rules:{
			name:{
				required:true,
				isName:true
			},
			sex:"required",
			birth:"required",
            mobile:{
				required:true,
				isPhone:true
			},
			email:{
				required:true,
				email:true
			}
		},
		messages:{
			name:{
				required:"氏名を入力してください",
				isName:"氏名漢字のみが使用可能です"
			},
			sex:{
				required:"性別を入力してください"
			},
			birth:{
				required:"生年月日を入力してください"
			},
            mobile:{
				required:"携帯電話番号を入力してください",
				isPhone:"正しい11桁の携帯電話番号を入力してください"
			},
			email:{
				required:"メールアドレスを入力してください",
				email:"正しいメールアドレスの形式で入力してください"
			}
		},
	
		errorPlacement:function(error,element){
			element.next().remove();
			element.closest('.gg-formGroup').append(error);
		},
		
		highlight:function(element){
			$(element).closest('.gg-formGroup').addClass('has-error has-feedback');
		},
		success:function(label){
			var el = label.closest('.gg-formGroup').find("input");
			el.next().remove();
			label.closest('.gg-formGroup').removeClass('has-error').addClass("has-feedback has-success");
			label.remove();
		},
		submitHandler:function(form){
			alert("保存成功!");
		}
	});
	
	//パスワード修正フォームを検証
	$("#modifyPwd").validate({
		onfocusout: function(element) { $(element).valid()},
		 debug:false, //バリデーションが通過した後、直接フォームを送信するかどうかを表示
		 onkeyup:false, //キーが離れたときにバリデーションをリッスンするかどうかを表示
		rules:{
			pwdOld:{
				required:true,
				minlength:6
			},
            pwdNew:{
			   required:true,
			   minlength:6,
			   isdiff:true,
			   //issame:true,
		   },
			confirm_password:{
			  required:true,
			  minlength:6,
			  issame:true,
			}
		  
		   },
		messages:{
			 	pwdOld : {
					 required:'必須項目',
					 minlength:$.validator.format('パスワードは6文字以上である必要があります')
				},
            	pwdNew:{
				   required:'必須項目',
				   minlength:$.validator.format('パスワードは6文字以上である必要があります'),
				   isdiff:'元のパスワードと新しいパスワードは同じにできません',
				  
			   },
				confirm_password:{
				   required:'必須項目',
				   minlength:$.validator.format('パスワードは6文字以上である必要があります'),
				   issame:'新しいパスワードと確認用パスワードが一致する必要があります',
				}
		
		},
		errorElement:"mes",
		errorClass:"gg-star",
		errorPlacement: function(error, element) 
		{ 
			element.closest('.gg-formGroup').append(error);

		}
	});
});