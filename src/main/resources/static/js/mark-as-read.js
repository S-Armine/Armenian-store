function markAsRead(notificationID) {
    const response = fetch('/company/notifications/mark/' + notificationID, {
        method: 'PUT'
    })
    if (response.ok) {
        location.reload();
    } else if (response.status === 403) {
        alert('Notification does not belong to this company');
    }
}
