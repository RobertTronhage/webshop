document.addEventListener("DOMContentLoaded", function() {
    // Check if the 'updateSuccess' query parameter is present
    if (window.location.search.indexOf('updateSuccess=true') > -1) {

        setTimeout(function() {
            window.location.href = '/users'; // This is where you redirect after 2 seconds
        }, 2000);
    }
});

