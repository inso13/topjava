var filter=false;

function makeEditable() {
    $('.delete').click(function () {
        deleteRow($(this).attr("id"));
    });

    $('#detailsForm').submit(function () {
        save();
        return false;
    });

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(event, jqXHR, options, jsExc);
    });
    init();
}

function changeStatus(id)
{
    $.ajax({
        url: ajaxUrl + id,
        type: 'POST',
        success: function () {
            successNoty('Changed status');
            init();
        }
    });
}

function add() {
    $('#id').val(null);
    $('#editRow').modal();
}

function deleteRow(id) {
    $.ajax({
        url: ajaxUrl + id,
        type: 'DELETE',
        success: function () {
            if (filter) filtered();
            else updateTable();
            successNoty('Deleted');
        }
    });
}

function save() {
    var form = $('#detailsForm');
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: form.serialize(),
        success: function () {
            $('#editRow').modal('hide');
            if (filter) filtered();
            else updateTable();
            successNoty('Saved');
        }
    });
}

function filtered() {
    $.ajax({
        type: 'POST',
        url: ajaxUrl + 'filter',
        data: $('#filterForm').serialize(),
        success: function (data) {
            datatableApi.clear();
            $.each(data, function (key, item) {
                datatableApi.row.add(item);
            });
            datatableApi.draw();
            successNoty('Filtered');
            filter=true;
        }
    });
}

function updateTable() {
    $.get(ajaxUrl, function (data) {
        datatableApi.clear();
        $.each(data, function (key, item) {
            datatableApi.row.add(item);
        });
        datatableApi.draw();
        filter=false;
        init();
    });
}

var failedNote;

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNoty(text) {
    closeNoty();
    noty({
        text: text,
        type: 'success',
        layout: 'bottomRight',
        timeout: true,
        theme: 'relax'
    });
}

function failNoty(event, jqXHR, options, jsExc) {
    closeNoty();
    failedNote = noty({
        text: 'Failed: ' + jqXHR.statusText + "<br>",
        type: 'error',
        layout: 'bottomRight',
        theme: 'relax'
    });
}

function init()
{
    $(':checkbox').each(function () {
        if (!$(this).is(":checked")) {$(this).parent().parent().css("text-decoration", "line-through");}
        else {$(this).parent().parent().css("text-decoration", "none");}
    });
}
