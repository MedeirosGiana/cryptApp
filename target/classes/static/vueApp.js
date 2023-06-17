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
          },
          saveCoin(){
               this.coin = []
               this.formCoin.name = this.formCoin.name.toUpperCase()
               this.formCoin.price = this.formCoin.price.replace("R$", '')
                    .replace(',', '.').trim()

               if(this.formCoin.name == '' || this.formCoin.price == '' || this.formCoin.quantity==''){
                  toastr.error('Todos os campos são obrigatórios.', 'Formulário')
                  return
               }

               const coin = {
                     name: this.formCoin.name,
                     price: this.formCoin.price,
                     quantity: this.formCoin.quantity
               }
               const self = this

               axios.post(baseUrl, coin)
               .then(function(response){
                    toastr.success('Transação cadastrada com sucesso.', 'Formulário')
               })
               .catch(function(error){
                   toastr.error('Não foi possível cadastrar nova transação.', 'Formulário')
               })
               .then(function(){
                   self.showAllCoins()
                   self.cleanForm()
               })
          },
          cleanForm(){
          this.formCoin.isNew = true
          this.formCoin.name = ''
          this.formCoin.price = ''
          this.formCoin.quantity = ''
          this.formCoin.title: 'Cadastrar nova Transação'
          this.formCoin.button: 'Cadastrar'
          }
      }
}

createApp(mainContainer).mount('#app')