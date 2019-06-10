var App = function() {
}

App.showModalComfirmDialog = (msgTxt, url) => {
  let modal = document.getElementById('modalDialog');
  let btnCancel = document.getElementById("btnCancel");
  let btnOk = document.getElementById("btnOk");
  let closeMark = document.getElementsByClassName("closeMark")[0];
  let msg = document.getElementById("msg");
  // メッセージの設定
  msg.innerText = msgTxt;

  // OK押下時
  btnOk.onclick = () => {
    modal.style.display = "none";
    location.href = url;
  }
  // キャンセル押下時
  btnCancel.onclick = () => {
    modal.style.display = "none";
  }
  // 「x」押下時
  closeMark.onclick = () => {
    modal.style.display = "none";
  }

  modal.style.display = "block";
}

App.showModalDialog = (msgTxt, url) => {
  let modal = document.getElementById('modalDialog');
  let btnCancel = document.getElementById("btnCancel");
  let btnOk = document.getElementById("btnOk");
  let closeMark = document.getElementsByClassName("closeMark")[0];
  let msg = document.getElementById("msg");
  // メッセージの設定
  msg.innerText = msgTxt;
  btnCancel.style.visibility = "hidden";

  // OK押下時
  btnOk.onclick = () => {
    modal.style.display = "none";
    if (url) location.href = url;
  }

  // 「x」押下時
  closeMark.onclick = () => {
    modal.style.display = "none";
  }

  modal.style.display = "block";
}
App.logout = () => {
  let message = "ログアウトしますがよろしいですか？";
  let url = "/inmane/security/logout";
  App.showModalComfirmDialog(message, url);
}
App.msgModal = (msg, url) => {
  showModalDialog(msg, url);
}
App.onclickUserName = () => {
  let chkUserName = document.getElementById("chkUserName");
  let btnLogout = document.getElementById("btnLogout");
  let btnSettings = document.getElementById("btnSettings");
  btnLogout.disabled = !chkUserName.checked;
  btnSettings.disabled = !chkUserName.checked;

}

