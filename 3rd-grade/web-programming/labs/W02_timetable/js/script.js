// This script runs after the HTML document has fully loaded
document.addEventListener('DOMContentLoaded', function() {
    
    // Find all elements with the class 'class-item'
    const classItems = document.querySelectorAll('.class-item');
    
    // Add a click event listener to each class item
    classItems.forEach(item => {
        item.addEventListener('click', function() {
            // Get the title and location text from the clicked item
            const title = item.querySelector('.title').textContent;
            const location = item.querySelector('.location').textContent;
            
            // Show an alert with the class details
            alert(`과목: ${title}\n강의실: ${location}`);
        });
    });
});