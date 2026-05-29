document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('hamburger').addEventListener('click', function() {
        document.getElementById('navLinks').classList.toggle('open');
    });
    document.querySelectorAll('.navbar__links a').forEach(link => {
        link.addEventListener('click', () => {
            document.getElementById('navLinks').classList.remove('open');
        });
    });
});