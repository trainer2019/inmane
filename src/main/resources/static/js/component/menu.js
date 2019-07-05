/* -------------------------------------------------------------------------- */
/* サイドメニュー用JS */
/* -------------------------------------------------------------------------- */
$(document).ready(() => {
  /* 初期表示 */
  $(() => {
    if (localStorage.getItem('sidebar') == 'true') {
      $('#sidebar').toggleClass('active');
    }
    
    showSubMenu();
    
    $('#sideMenu').show();
  });
  /* サブメニュー表示 */
  const showSubMenu = () => {
    showMenu($('#mainMenu'), $('#mainSubMenu'));
    showMenu($('#menteMenu'), $('#menteSubMenu'));
  }
  /* メニュー表示 */
  const showMenu = (parent, target) => {
    const flg = target.find('li').hasClass('selected');
    if (flg) {
      parent.attr('aria-expanded', 'true');
      parent.addClass('activeMenu');
      target.show();
    } else {
      parent.attr('aria-expanded', 'false');
      parent.removeClass('activeMenu');
      target.hide();
    }
  }
  /* sidebarボタンクリック時処理 */
  $('#sidebarCollapse').on('click', () => {
    const sideMenu = $('#sideMenu');
    
    sideMenu.hide();
    
    $('#sidebar, #content').toggleClass('active');
    $('a[aria-expanded=true]').attr('aria-expanded', 'false');
    
    showSubMenu();
    
    const isActive = $('#sidebar').hasClass('active');
    localStorage.setItem('sidebar', isActive);
    
    fadeTitle();
    
    sideMenu.fadeIn('slow');
  });
  
  $('#mainMenu').on('click', () => {
    const menu = $('#mainMenu');
    const subMenu = $('#mainSubMenu');
    
    menuToggle(menu, subMenu);
  });
  
  $('#menteMenu').on('click', () => {
    const menu = $('#menteMenu');
    const subMenu = $('#menteSubMenu');
    
    menuToggle(menu, subMenu);
  });
  fadeTitle();
});

/* サブメニュー用toggle */
const menuToggle = (parent, target) => {
  const expanded = parent.attr('aria-expanded') == 'true';
  
  parent.attr('aria-expanded', expanded ? 'false' : 'true');
  expanded ? parent.removeClass('activeMenu') : parent.addClass('activeMenu');
  
  target.slideToggle(500);
};

/* サイドバーヘッダアニメーション */
const fadeTitle = () => {
  const $allMsg = $('#title');
  const $wordList = $allMsg.attr('title').split('');
  $('#title').html("");
  $.each($wordList, (idx, elem) => {
    const newEL = $('<span>').text(elem).css({ opacity: 0 });
    newEL.appendTo($allMsg);
    newEL.delay(idx * 70);
    newEL.animate({ opacity: 1 }, 1100);
  });
};