$(function(){
    //购物车总价
    let array = $(".cost");
    let totalCost = 0;
    for(let i = 0;i < array.length;i++){
        let val = parseInt($(".cost").eq(i).html());
        totalCost += val;
    }
    $("#totalCost").html("￥"+totalCost);
});
$(function(){
    //商品类目
    $(".leftNav ul li").hover(
        function(){
            $(this).find(".fj").addClass("nuw");
            $(this).find(".zj").show();
        }
        ,
        function(){
            $(this).find(".fj").removeClass("nuw");
            $(this).find(".zj").hide();
        }
    )
});