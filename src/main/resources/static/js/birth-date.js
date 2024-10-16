function populateYearSelect() {
    const yearSelect = document.getElementById('year');
    const currentYear = new Date().getFullYear();
    const startYear = currentYear - 90;
    const endYear = currentYear - 18;

    for (let year = endYear; year >= startYear; year--) {
        const option = document.createElement('option');
        option.value = year;
        option.textContent = year;
        yearSelect.appendChild(option);
    }
}

function populateDaySelect() {
    const daySelect = document.getElementById('day');
    for (let day = 1; day <= 31; day++) {
        const option = document.createElement('option');
        option.value = day;
        option.textContent = day;
        daySelect.appendChild(option);
    }
}

document.addEventListener('DOMContentLoaded', function() {
    populateYearSelect();
    populateDaySelect();
});

function validateDay() {
    const year = parseInt(document.getElementById("year").value, 10);
    const month = parseInt(document.getElementById("month").value, 10);
    const day = parseInt(document.getElementById("day").value, 10);
    const startDay = 1;
    let endDate;
    if (month === 2 && leapYear(year)) {
        endDate = 29;
    } else if (month === 2 && !leapYear(year)) {
        endDate = 28;
    } else if (has31Days(month)) {
        endDate = 31
    } else {
        endDate = 30
    }
    if (day < startDay || day > endDate) {
        return false;
    } else {
        return true;
    }
}

function leapYear(year)
{
  return ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0);
}

function has31Days(month) {
    if ((month < 8 && month % 2 === 1) || (month >= 8 && month % 2 === 0)) {
        return true;
    } else {
        return false
    }
}

document.addEventListener("submit", function(event) {
    if (!validateDay()) {
        alert("Specified day is invalid for selected month.")
        event.preventDefault();
    }
})


