var index = 0;
showSlides();

function showSlides() {
  var slides = document.getElementsByClassName('slides');
  for (var i = 0; i < slides.length; i++) {
    slides[i].style.display = "none";
  }
  index++;
  if (index > slides.length) {
    index = 1;
  }
  slides[index - 1].style.display = "block";
  setTimeout(showSlides, 3000);
}