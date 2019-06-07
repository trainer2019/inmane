var App = function() {
}

/* -------------------------------------------------------------------------- */
/* 共通ユーティリティ */
/* -------------------------------------------------------------------------- */
App.zenNum2HanNum = function(num) {
	var z = [ "０", "１", "２", "３", "４", "５", "６", "７", "８", "９" ];
	for (var i = 0; i < 10; i++)
		num = num.replace(new RegExp(z[i], "g"), i);
	num = num.replace(RegExp("[^0-9]", "g"), "");
	return num;
}
App.convHMFormat = function(input) {

	var hh = "00";
	var mm = "00";
	var splithhmm = "";
	var inputvalue = $(input).val();
	var inputvalueOld = inputvalue;

	var inputvaluelength = inputvalue.length;

	// 0文字の時は00:00で更新
	if (inputvaluelength == 0) {
		inputvalue = "00:00";
	}
	// ：が大文字の場合は小文字に置き換え
	inputvalue.replace("：", ":");

	if (inputvalue.indexOf(":") != -1) {
		// :が入っているとき
		splithhmm = inputvalue.split(":");
		/* 数値型項目 全角を半角に変換 */
		splithhmm[0] = App.zenNum2HanNum(splithhmm[0]);
		splithhmm[1] = App.zenNum2HanNum(splithhmm[1]);
		// ゼロ埋め
		hh = (hh + splithhmm[0]).slice(-2);
		mm = (mm + splithhmm[1]).slice(-2);
	} else {
		// :が入っていないとき
		/* 数値型項目 全角を半角に変換 */
		inputvalue = App.zenNum2HanNum(inputvalue);
		if (inputvaluelength < 3) {
			// hhをゼロ埋め
			hh = (hh + inputvalue).slice(-2)
			// mmは00のまま
		} else if (inputvaluelength == 3) {
			hh = (hh + inputvalue).slice(1, 3);
			mm = inputvalue.slice(-2);
		} else {
			hh = inputvalue.slice(0, 2);
			mm = inputvalue.slice(-2);
		}
	}
	inputvalue = hh + ":" + mm;
	// typeに応じてヴァリデーションを切り替える(休憩時間は最大で10時間未満←制限を外した
	if (inputvalue.match(/([0-1]{1}[0-9]{1}|2[0-8]{1}):[0-5][0-9]|29:00/)) {
		$(input).val(inputvalue);
	} else {
		$(input).val("");
		$(input).focus();
	}
	if(inputvalueOld != $(input).val()){
		$(input).change();
	}
}
/* -------------------------------------------------------------------------- */
/* 画面読込・画面遷移関数 */
/* -------------------------------------------------------------------------- */
App.contextPath = function() {
	return $("body").data("contextPath");
}
App.apiPath = function(path) {
	return App.contextPath() + $("body").data("selectedOfficeId") + "/" + path;
}
App.complete = function(message, url) {
	if (url) {
		$("#formCompleteMessage").val(message);
		$("#formCompleteUrl").val(url);
		App.submit("#formComplete");
	} else if (message) {
		App.dialogComplete(message);
	}
}
App.logout = function() {
	App.ajax({
		url : App.contextPath() + 'security/logout',
		success : function(data, textStatus, jqXHR) {
			App.redirect(App.contextPath());
		}
	});
}
App.redirect = function(url) {
	$(location).attr("href", url);
}
App.submit = function(form) {
	if (form) {
		$(form).removeAttr("onsubmit");
		$(form).submit();
	}
}
App.doOnLoad = function() {
}

$(function() {

	// CSRF対応
	$(document).ajaxSend(
			function(e, xhr, options) {
				xhr.setRequestHeader($("meta[name='_csrf_header']").attr(
						"content"), $("meta[name='_csrf']").attr("content"));
			});

	// ajaxローディング設定
	$(document).ajaxStart(function() {
		$("#loading").fadeIn("fast");
	});
	$(document).ajaxStop(function() {
		$("#loading").fadeOut("fast");
	});

	// Flashスコープの例外メッセージ表示
	if ($("#appCompleteMessageFailure").val()) {
		App.alert($("#appCompleteMessageFailure").val(), function() {
			App.redirect(App.apiPath("calendar"));
		});
	}
	// Flashスコープの警告メッセージ表示
	else if ($("#appCompleteMessageWarning").val()) {
		App.dialogAlert($("#appCompleteMessageWarning").val());
	}
	// Flashスコープの完了メッセージ表示
	else if ($("#appCompleteMessageSuccess").val()) {
		App.dialogComplete($("#appCompleteMessageSuccess").val());
	}

	// セレクトボックス
	$('.select2').each(
			function() {
				App.select2(this,
						{
							minimumResultsForSearch : $(this).data(
									"resultsForSearch") ? Number($(this).data(
									"resultsForSearch")) : -1,
						});
			});
	// datepicker
	$('.datepicker').each(function() {
		App.flatpickr(this);
	});

	// HHMM入力
	$(".hhmm").each(function() {
		$(this).on("blur", function() {
			App.convHMFormat(this);
		});
		$(this).on("focus", function() {
			this.select();
		});
	});

	// initialization of header
	$.HSCore.components.HSHeader.init($('#js-header'));
	$.HSCore.helpers.HSHamburgers.init('.hamburger');
	
	// initialization of carousel
	$.HSCore.components.HSCarousel.init('.js-carousel');
	$('.js-carousel').hide().fadeIn('slow');
	
	// initialization of tabs
	$.HSCore.components.HSTabs.init('[role="tablist"]');
	
	// initialization of header's height equal offset
	$.HSCore.helpers.HSHeightCalc.init();
	
	// initialization of go to
	$.HSCore.components.HSGoTo.init('.js-go-to');
	
	// Form Focus State
	$.HSCore.helpers.HSFocusState.init();
	
	// initialization of custom select
	// $('.js-select').selectpicker();

	// initialization of range datepicker
//	$.HSCore.components.HSRangeDatepicker.init('.js-range-datepicker');

	// initialization of sidebar navigation component
//	$.HSCore.components.HSSideNav.init('.js-side-nav');

	// initialization of hamburger
	// $.HSCore.helpers.HSHamburgers.init('.hamburger');

	// initialization of HSDropdown component
//	$.HSCore.components.HSDropdown.init($('[data-dropdown-target]'), {
//		dropdownHideOnScroll : false
//	});

	// initialization of popups
	// $.HSCore.components.HSPopup
	// .init(
	// '.js-fancybox',
	// {
	// btnTpl : {
	// smallBtn : '<button data-fancybox-close class="btn g-pos-abs g-top-25
	// g-right-30 g-line-height-1 g-bg-transparent g-font-size-16
	// g-color-gray-light-v6 g-brd-none p-0" title=""><i
	// class="hs-admin-close"></i></button>'
	// }
	// });

	// initialization of custom scrollbar
	// $.HSCore.components.HSScrollBar.init($('.js-custom-scroll'));

	// initialization of forms
	// $.HSCore.components.HSCountQty.init('.js-quantity');

	// 画面固有初期化処理
	App.doOnLoad();
});
/* -------------------------------------------------------------------------- */
/* ダイアログ */
/* -------------------------------------------------------------------------- */
App.dialogNotification = function() {
	if($("#notificationsMenu").size() != 0 ) {
		return;
	}
	// 初回クリックのみ通知情報を取得
	$.get(App.apiPath("frame/notification"), function(data) {
		
		// 通知情報の設定
		$("#notificationsInvoker").attr("data-dropdown-target", "#notificationsMenu").after(data);
		
		// ドロップダウン設定
		$.HSCore.components.HSDropdown.init($("#notificationsInvoker"), {
			dropdownHideOnScroll : false
		});
		// スクロール設定
		$.HSCore.components.HSScrollBar.init($('#notificationsMenu'));
		// 通知を開く
		$("#notificationsInvoker").click();
	});
}
App.dialogComplete = function(message) {
	new Noty(
			{
				type : "success",
				layout : "bottomRight",
				timeout : "5000",
				animation : {
					open : "animated fadeIn",
					close : "animated fadeOut"
				},
				closeWith : [ "click" ],
				text : "<div class=\"g-mr-20\"><div class=\"noty_body__icon\"><i class=\"hs-admin-check\"></i></div></div><div>"
						+ message + "</div>",
				theme : "unify--v1"
			}).show();
}
App.dialogSuccess = function(message, callback) {
	swal({
		title : "",
		text : message,
		type : "success",
		buttonsStyling : false,
		confirmButtonText : 'OK',
		confirmButtonClass : 'btn btn-md g-width-160--md g-mx-2 u-btn-primary',
	}).then(function() {
		if (callback) {
			callback();
		}
	});
}
App.dialogInfo = function(message, callback) {
	swal({
		title : "",
		text : message,
		type : "info",
		buttonsStyling : false,
		confirmButtonText : 'はい',
		confirmButtonClass : 'btn btn-md g-width-160--md g-mx-2 u-btn-blue',
	}).then(function() {
		if (callback) {
			callback();
		}
	});
}
App.dialogAlert = function(message, callback) {
	swal({
		title : "",
		text : message,
		type : "warning",
		buttonsStyling : false,
		confirmButtonText : 'OK',
		confirmButtonClass : 'btn btn-md g-width-160--md g-mx-2 u-btn-orange',
	}).then(function(state) {
		setTimeout(function() {
			if (callback) {
				callback();
			}
		}, 100);
	});
}
App.dialogConfirm = function(message, callback) {
	swal(
			{
				title : "",
				text : message,
				type : "info",
				showCancelButton : true,
				buttonsStyling : false,
				confirmButtonText : 'はい',
				cancelButtonText : 'いいえ',
				confirmButtonClass : 'btn btn-md g-width-160--md g-mx-2 u-btn-blue',
				cancelButtonClass : 'btn btn-md g-width-160--md g-mx-2 u-btn-outline-gray-dark-v6',
			}).then(function(state) {
		if (callback) {
			callback(state.value);
		}
	});
}
App.dialogConfirmWarning = function(message, callback) {
	swal(
			{
				title : "",
				text : message,
				type : "warning",
				showCancelButton : true,
				buttonsStyling : false,
				confirmButtonText : 'はい',
				cancelButtonText : 'いいえ',
				confirmButtonClass : 'btn btn-md g-width-160--md g-mx-2 u-btn-orange',
				cancelButtonClass : 'btn btn-md g-width-160--md g-mx-2 u-btn-outline-gray-dark-v6',
			}).then(function(state) {
		if (callback) {
			callback(state.value);
		}
	});
}

/* -------------------------------------------------------------------------- */
/* エラー制御 */
/* -------------------------------------------------------------------------- */
App.btnEnabled = function(scope, btnSelector) {

	App.btnEnabledCheck(scope, btnSelector);

	$(document)
			.on(
					"input change",
					$(scope)
							.find(
									"input.required, select.required, textarea.required"),
					function() {
						App.btnEnabledCheck(scope, btnSelector)
					});
}
App.btnEnabledCheck = function(scope, btnSelector) {

	var empty = false;

	$(scope).find("input.required,select.required, textarea.required").each(
			function() {
				if ($(this).val() == "") {
					$(btnSelector).prop("disabled", true);
					empty = true;
					return false;
				}
			});
	if (!empty) {
		$(btnSelector).prop("disabled", false);
	}
}

App.errorReset = function(scope) {

	if (!scope) {
		scope = $("body");
	}

	$(scope).find(".valid-error").removeClass("valid-error");
	$(scope).find(".valid-error-msg").html("");
}
App.errorMsg = function(scope, name, message) {
	
	$input = $(scope).find("[name='" + name + "'][type!='hidden']");

	if ($input.size() > 0) {

		$input.addClass("valid-error");

		if ($(scope).find("." + name + "-msg").size() == 0) {
			$input.after("<span class='" + name
					+ "-msg valid-error-msg'></span>");
		}
	}

	$(scope).find("." + name + "-msg").hide().addClass("valid-error-msg").html(
			"<span>" + message + "</span>").fadeIn();
}

/* -------------------------------------------------------------------------- */
/* フォーム制御 */
/* -------------------------------------------------------------------------- */
App.clearForm = function(scope) {

	App.errorReset(scope);

	$(scope)
			.find(
					"input:text, input[type='tel'], input[type='url'], input:password, textarea")
			.val("");

	$(scope).find(".select2").each(function() {
		var val = $(this).find("option:first").val();
		$(this).val(val).trigger("change");
	});
	$(scope).find('input:checkbox').prop("checked", false);
}
App.readonly = function(form) {

	$(form).addClass("readonly").find("input,textarea").prop('readonly', true)
			.filter(':checkbox, :radio').on('click', App.inputDisabledHandler);
	$(form).find("select").children(':not(:selected)').attr('disabled', true);
	$(form).find('button').prop("disabled", true);

}
App.readonlyCancel = function(input) {

	$(input).removeClass("readonly").find(
			'input:not(.readonly,.mask),textarea:not(.readonly)').prop(
			'readonly', false).filter(':checkbox, :radio').off('click',
			App.inputDisabledHandler);
	$(input).find('select:not(.readonly)').children().attr('disabled', false);
	$(input).find('button:not(.readonly)').prop("disabled", false);
	App.datepicker();
}
App.inputDisabledHandler = function(e) {
	e.preventDefault();
	return false;
}

/* -------------------------------------------------------------------------- */
/* 非同期通信 */
/* -------------------------------------------------------------------------- */
App.ajax = function(options) {

	// プロパティの設定
	var opt = $.extend({}, $.ajaxSettings, {
		type : "post",
		dataType : "json",
		beforeSend : function(xhr) {
			xhr.setRequestHeader(
					$("meta[name='_csrf_header']").attr("content"), $(
							"meta[name='_csrf']").attr("content"));
		},
	}, options);

	// フォームが指定されている場合
	if (opt.form && $(opt.form).size() > 0) {
		opt.data = $(opt.form).serializeArray();
	}

	opt.success = (function(func) {
		return function(data, statusText, jqXHR) {

			// 警告終了の場合はエラーメッセージを表示
			if (data.status == 2) {
				
				// スコープの設定
				if (opt.scope && $(opt.scope).size() > 0) {
					$scope = $(opt.scope);
				} else if (opt.form && $(opt.form).size() > 0) {
					$scope = $(opt.form);
				} else {
					$scope = $("body");
				}
				// エラーメッセージの初期化
				App.errorReset($scope);
				// メッセージの設定
				$.each(data.errors, function(key, val) {

					// 共通エラーメッセージ
					if (key == "appErrorMessage") {
						var msg = "";
						for (var i = 0; i < val.length; i++) {
							msg += val[i] + "\n";
						}

						App.dialogAlert(msg);
					}
					// 個別エラーメッセージ
					else {
						var msg = "";
						for (var i = 0; i < val.length; i++) {
							msg += "<p>" + val[i] + "</p>";
						}

						App.errorMsg($scope, key, msg);
					}
				});
				// 自動スクロール
				// if ($(".valid-error:first").size() > 0) {
				// $("html, body").animate({
				// scrollTop : $(".valid-error:first").offset().top
				// }, 1100);
				// return;
				// }

				if (opt.warning) {
					opt.warning(data.data, statusText, jqXHR);
				}

				return false;
			}
			// 例外終了の場合はシステムエラー画面に強制リダイレクト
			else if (data.status == 3) {

				$msg = "";

				$.each(data.errors, function(key, val) {

					// 共通エラーメッセージのみ取得
					if (key == "appErrorMessage") {
						for (var i = 0; i < val.length; i++) {
							$msg += "" + val[i] + "\n";
						}
					}
				});

				if ($msg) {
					App.dialogAlert($msg, function() {
						App.redirect(App.apiPath("calendar"));
					})
				} else {
					App.redirect(App.apiPath("calendar"));
				}

				return false;
			}
			// 正常終了の場合
			else if (data.status == 1) {
				if (func) {
					func(data.data, statusText, jqXHR);
				}
			}
		};
	})(opt.success);

	opt.error = (function(func) {
		return function(jqXHR) {

			if (func) {
				func(jqXHR);
			} else {
				// 各種エラー画面に遷移
				if (jqXHR.status === 403) {
					App.redirect(App.contextPath() + "");
				} 
				else if (jqXHR.status === 404) {
					App.redirect(App.contextPath() + "error/404");
				} 
				else {
					App.redirect(App.contextPath() + "error/500");
				}
			}
		};
	})(opt.error);

	opt.complete = (function(func) {
		return function(jqXHR, statusText) {
			if (func) {
				func(jqXHR, statusText);
			}
		};
	})(opt.complete);

	return $.ajax(opt);
};

/* -------------------------------------------------------------------------- */
/* flatpickr */
/* -------------------------------------------------------------------------- */
App.flatpickr = function(selector, options) {

	// クラスの追加
	$(selector).addClass("datepicker");
	// プロパティの設定
	var opt = $.extend({
		dateFormat : 'Y/m/d',
		locale : {
			firstDayOfWeek : 1,
			weekdays : {
				shorthand : [ "日", "月", "火", "水", "木", "金", "土" ],
				longhand : [ "日曜日", "月曜日", "火曜日", "水曜日", "木曜日", "金曜日", "土曜日" ]
			},
			months : {
				shorthand : [ "1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月",
						"9月", "10月", "11月", "12月" ],
				longhand : [ "1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月",
						"9月", "10月", "11月", "12月" ]
			}
		},
		onReady : function(dateObj, dateStr, instance) {
			// クリアボタンの追加
			$('.flatpickr-calendar').each(function() {
				var $this = $(this);
				if ($this.find('.flatpickr-clear').length < 1) {
					$this.append('<div class="flatpickr-clear">クリア</div>');
					$this.find('.flatpickr-clear').on('click', function() {
						instance.clear();
						instance.close();
					});
				}
			});
			// カレンダーアイコンの設定を追加
			$(selector).next().find("i.hs-admin-calendar").css('cursor',
					'pointer').on('click', function() {
				instance.open();
			});
		}
	}, options);

	$(selector).flatpickr(opt);
}
/* -------------------------------------------------------------------------- */
/* Select2 */
/* -------------------------------------------------------------------------- */
App.select2 = function(selector, options) {

	// クラスの追加
	$(selector).addClass("select2");
	// プロパティの設定
	var opt = $.extend({
		dropdownAutoWidth : true,
		width : '100%',
		minimumResultsForSearch : Infinity,
	}, options);
	// select2の設定
	$(selector).select2(opt).maximizeSelect2Height();
	// アローアイコンの設定
	$(selector).nextAll("i.hs-admin-angle-down").css('cursor', 'pointer').on(
			'click', function() {
				$(selector).select2("open");
			});
}
/* -------------------------------------------------------------------------- */
/* Datatable */
/* -------------------------------------------------------------------------- */
App.dataTable = function(selector, options) {

	// 条件の取得
	var prefix = "#" + $(selector).attr('id');
	var pageOffset = $(prefix + "PageOffset").val();
	var sortIndex = $(prefix + "SortIndex").val();
	var sortType = $(prefix + "SortType").val();

	// プロパティの設定
	var opt = $.extend({
		responsive : true,
		serverSide : true,
		searching : false,
		lengthChange : false,
		displayStart : pageOffset ? Number(pageOffset) : 0,
		pageLength : 50,
		order : sortIndex ? [ [ Number(sortIndex), sortType ] ] : [],
		ajax : {
			type : "POST"
		},
		drawCallback : function(settings) {
			if ($(prefix + "PageOffset").size() > 0) {
				$(prefix + "PageOffset").val(settings.json.pageOffset);
			}
			if ($(prefix + "SortIndex").size() > 0) {
				$(prefix + "SortIndex").val(settings.json.sortIndex);
			}
			if ($(prefix + "SortType").size() > 0) {
				$(prefix + "SortType").val(settings.json.sortType);
			}
		},
		language : {
			// sProcessing: "処理中...",
			sLengthMenu : "_MENU_ 件表示",
			sZeroRecords : "データはありません。",
			sInfo : " _TOTAL_ 件中 _START_ から _END_ まで表示",
			sInfoEmpty : " 0 件中 0 から 0 まで表示",
			sInfoFiltered : "（全 _MAX_ 件より抽出）",
			sInfoPostFix : "",
			sSearch : "検索:",
			sUrl : "",
			processing : '<i class="fa fa-spinner fa-spin"></i>',
			paginate : {
				previous : '<i class="hs-admin-angle-left"></i>',
				next : '<i class="hs-admin-angle-right"></i>'
			},
		},
	}, options);

	$(selector).DataTable(opt);
}