const { createApp } = Vue
const baseUrl = "http://localhost:8080/coin"
const mainContainer = {
   data() {
       return {
       coins:[]
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
              this.coins.push({
                name: item.name,
                quantity: item.quantity
              })
           })
        })
     }
   }
}

createApp(mainContainer).mount('#app')