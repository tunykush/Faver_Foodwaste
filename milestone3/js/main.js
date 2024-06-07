
/*
document.addEventListener("DOMContentLoaded", function(event) {
    console.log('abc');
    const content = document.getElementById('content');
    const progressBar = document.getElementById('progress-bar');
    window.addEventListener('scroll', () => {
            /*const scrollTop = .scrollTop();
            const scrollHeight = window.innerHeight;
            const percentage = (scrollTop / scrollHeight) * 100;
            progressBar.style.width = percentage + '%';
            console.log(percentage);
            console.log("height",scrollHeight);
            console.log(content.scrollHeight,window.scrollY,document.documentElement.scrollTop);
            console.log("top",scrollTop);
            let scrollTop = window.scrollY;
            let docHeight = document.body.offsetHeight;
            let winHeight = window.innerHeight;

            console.log(scrollTop,docHeight,winHeight);

            let scrollPercent = scrollTop / (docHeight - winHeight);
            let scrollPercentRounded = Math.round(scrollPercent * 100);
            console.log(`(${scrollPercentRounded}%)`);
            },true);
});

*/
/*const content = document.getElementById('content')
        const progressBar = document.getElementById('progress-bar')

        document.addEventListener('scroll', () => {
            const scrollTop = window.scrollY || document.documentElement.scrollTop
            const scrollHeight = content.scrollHeight - window.innerHeight
            const percentage = (scrollTop / scrollHeight) * 100
            console.log(percentage);//progressBar.style.width = percentage + '%'
        })*/

            const content = document.getElementById('content')
        const progressBar = document.getElementById('progress-bar')

        document.addEventListener('scroll', () => {
            const scrollTop = window.scrollY || document.documentElement.scrollTop
            const scrollHeight = content.scrollHeight - window.innerHeight
            const percentage = (scrollTop / scrollHeight) * 100
            progressBar.style.width = percentage + '%'
        });