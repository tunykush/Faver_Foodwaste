const content = document.getElementById('content')
        const progressBar = document.getElementById('progress-bar')

        document.addEventListener('scroll', () => {
            const scrollTop = window.scrollY || document.documentElement.scrollTop
            const scrollHeight = content.scrollHeight - window.innerHeight
            const percentage = (scrollTop / scrollHeight) * 100
            progressBar.style.width = percentage + '%'
        });