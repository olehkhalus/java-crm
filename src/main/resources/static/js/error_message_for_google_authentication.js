function errorMessageForGoogleAuthentication() {
    return swal({
        title: "Your Google login wasn't successful for some reason",
        text: "The email address you've entered doesn't appear to exist. " +
            "Please check your entry and try again.",
        type: "error",
        icon: "error",
    });
}