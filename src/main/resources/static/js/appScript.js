/**
 * Created by root on 18/7/18.
 */
$(document).ready(function () {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

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

    $('#playingCatSubmit').on('click', function () {
        var asOndate = $('#asOnDateAndMonth').val();
        $('#asOnDate').val(asOndate.substring(0,2));
        $('#asOnMonth').val(asOndate.substring(3));
        $('#playingCatForm').submit();
    })

    $('#payment-submit').on('click', function () {
        var allowSubmit = false;
        $('.feeCheckBox:checked').each(function (i) {
            allowSubmit = true;
            $('<input>').attr({
                type: 'hidden',
                name: 'feeList['+i+'].id',
                value: this.value
            }).appendTo('#payment-form');
        })
        if (allowSubmit)
            $('#payment-form').submit();
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
        if(validateFormFields())
            $('#eventRegistration-form').submit();
    })

    $('.eventRegistration-playing-category').on('change', function () {
        var totalFee=0.00;
        $('.eventRegistration-playing-category:checked').each(function (i) {
            totalFee+=Number($(this).data('fee'))
        })
        $('#total-event-reg-fee').text('Total : ₹ '+totalFee.toFixed(2))
    })

    $('#approveMember').on('change', function () {
        $.ajax({
            type: 'POST',
            url: "/a/member-details-approval",
            data: {"id" :this.value},
            beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
            }
        }).done( function(resultData) {
            var title='Alert Message'
            if (resultData) {
                var message='Updated member details successfully'
                messageAlertModal(title, message)
            }else {
                var message='Failed to Update'
                messageAlertModal(title, message)
                location.reload()
            }
        });
    })
})
function messageAlertModal(title, message) {
    $('#messageTitle').text(title)
    $('#message').text(message)
    $('#messageModal').modal('show')
}

function validateFormFields() {
    var valid=true;
    $('#eventRegistration-form input[required]').each(function () {
        if(!$(this).val()) {
            messageAlertModal('Alert Message', 'Please enter the required fields')
            $(this).css("border-color", "red");
            valid = false;
            return false;
        }
    })
    if(valid && $('.eventRegistration-playing-category:checked').length<1){
        messageAlertModal('Alert Message', 'Please choose any one playing category')
        $(this).css("border-color", "red");
        valid=false
        return false
    }
    return valid;
}