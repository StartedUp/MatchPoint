/**
 * Created by root on 18/7/18.
 */
$(document).ready(function () {

    $( ".datepicker" ).datepicker({changeYear: true, yearRange: "-80:+0", dateFormat: 'dd/mm/yy' });

    $('#create-event-submit').on('click', function () {
        $('.event-playing-category:checked').each(function (i) {
            $('<input>').attr({
                type: 'hidden',
                name: 'playingCategories['+i+'].id',
                value: this.value
            }).appendTo('#create-event-form');
        })
        $('#create-event-form').submit();
    })

    $('#register-event-submit').on('click', function () {
        $('.eventRegistration-playing-category:checked').each(function (i) {
            $('<input>').attr({
                type: 'hidden',
                name: 'playingCategories['+i+'].id',
                value: this.value
            }).appendTo('#eventRegistration-form');
            $('<input>').attr({
                type: 'hidden',
                name: 'playingCategories['+i+'].fee',
                value: Number($(this).data('fee'))
            }).appendTo('#eventRegistration-form');
        })
        $('#eventRegistration-form').submit();
    })

    $('.eventRegistration-playing-category').on('change', function () {
        var totalFee=0.00;
        $('.eventRegistration-playing-category:checked').each(function (i) {
            totalFee+=Number($(this).data('fee'))
        })
        $('#total-event-reg-fee').text('Total : ₹ '+totalFee.toFixed(2))
    })


})