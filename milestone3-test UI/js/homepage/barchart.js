const rating = document.getElementsByClassName('rating')[0];
          const block = document.getElementsByClassName('block');
  
          for (var i = 1; i < 100; i++){
              rating.innerHTML +="<div class ='block'></div>";
              block[i].style.transform = "rotate("+ 3.6 * i+"deg)";
              block[i].style.animationDelay ='${i/40}s'
          }
  
          const counter = document.querySelector('.counter');
          counter.innerText = 61.1;
  
          const target = +counter.getAttribute('data-target');
  
          const NumberCounter =() => {
              const value = +counter.innerText;
              if (value < target){
                  counter.innerText = Math.ceil(value +1);
                  setTimeout(() => {
                      NumberCounter()
                  },25)
              }
          }