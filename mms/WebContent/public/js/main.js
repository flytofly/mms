/*优化建议*/
function popWin(popWin,touch){
    var parent = document.getElementById(popWin);
    var touchItem = document.getElementById(touch);
    var goback = document.getElementById("goback");
    var close = document.getElementById("close");
    var masklayer = document.createElement("div");
        masklayer.setAttribute("class","masklayer");
        document.body.appendChild(masklayer);
        /*点击优化*/
        touchItem.onclick = function(){
            var parentHeight;
               parent.style.display = 'block';
               masklayer.style.display = 'block';
               parentHeight = parent.offsetHeight;
               parent.style.marginTop = (-parentHeight/2)+'px';
        }
        /*click 返回*/
        if(goback != undefined){
            goback.onclick = closeWin;
        }
        
        close.onclick = closeWin;
        masklayer.onclick = closeWin;

        function closeWin(){
            parent.style.display = 'none';
            masklayer.style.display = 'none';
        }
}


/*select*/
$.selectBox = function(){
    var selectBox = $('.select-box');
    selectBox.click(function(e){
        var $this = $(this);
        var dropBox = $this.find('.drop-datas');
        var datas = dropBox.find('li');
        var repData = $this.find('.choose-data span');
        var hasClass = $this.hasClass('active');
            e.stopPropagation();
            if(hasClass){
                $this.removeClass('active');
                dropBox.hide();
            }
            else{                
                $this.addClass('active');
                dropBox.show();
            }
            datas.click(function(e){
                var getData = $(this).text();                
                    e.stopPropagation();
                    repData.text(getData);
                    $this.removeClass('active');
                    dropBox.hide();
            });
            $('html,body').click(function(){
                $this.removeClass('active');
                dropBox.hide();
            });
    });

}