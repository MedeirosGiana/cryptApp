const { createApp } = Vue

const baseUrl = "http://localhost:8080/coin"

const mainContainer = {
      data() {
          return {
            coins:[],
            formCoin: {
                isNew: true,
                name: '',
                price: '',
                quantity: '',
                title: 'Cadastrar nova Transação',
                button: 'Cadastrar'
            }
          }
      },
      mounted() {
        this.showAllCoins()
      },
       methods: {
           showAllCoins(){
              axios
              .get(baseUrl)
              .then(response => {
                  response.data.forEach(item => {
                     this.coins.push(item)
                  })
             })
          }
      }
}

createApp(mainContainer).mount('#app')