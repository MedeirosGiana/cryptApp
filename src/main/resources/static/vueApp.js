const { createApp } = Vue

const baseUrl = "http://localhost:8080/coin/"

const mainContainer = {
    data() {
        return {
            coins:[],
            canSeeTransactions: false,
            formCoin: {
                isNew: true,
                name: '',
                price: '',
                quantity: '',
                title: 'Cadastrar nova transação',
                button: 'Cadastrar'
            },

            transactions: []
        }
    },

    mounted(){
         this.showAllCoins()
    },

    methods:{
        showAllCoins(){
            this.coins = []
            axios
                .get(baseUrl)
                .then(response => {
                    response.data.forEach(item => {
                        this.coins.push(item)
                    })
                })
        },
        showTransactions(name){
            this.transactions = {
                coinName: name,
                data: []
            }

            this.canSeeTransactions = true

            axios.get(baseUrl + name)
                .then(response => {
                    console.log(response)
                    response.data.forEach(item => {
                        this.transactions.data.push({
                            id: item.id,
                            name: item.name,
                            price: item.price.toLocaleString('pt-br', { style: 'currency', currency: 'BRL' }),
                            quantity: item.quantity,
                            datetime: this.formattedDate(item.dateTime)
                        })
                    })
                })
                .catch(function (error){
                    toastr.error(error)
                })
        },

       saveCoin(){
            this.coins = []
            this.formCoin.name = this.formCoin.name.toUpperCase()
            this.formCoin.price =  this.formCoin.price.replace("R$", '')
                .replace(',', '.').trim()

                if(this.formCoin.name == '' || this.formCoin.price == '' || this.formCoin.quantity == '') {
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
                    .then(function (response){
                        toastr.success('Nova transação cadastrada com sucesso!', 'Formulário')
                    })
                    .catch(function (error){
                        toastr.error('Não foi possível cadastrar uma nova transação.', 'Formulário')
                    })
                    .then(function(){
                        self.showAllCoins()
                        self.showTransactions(self.formCoin.name)
                        self.cleanForm()
                    })
                },

                removeTransaction(transaction){
                    const self = this

                    axios.delete(baseUrl + transaction.id)
                       .then(function(error){
                          toastr.success('Transação removida com sucesso!', 'Exclusão')
                       })
                       .catch(function (error){
                           toastr.error('Não foi possível remover a transação' + error, 'Exclusão')
                      })
                      .then(function (){
                       self.showAllCoins()
                       self.showAllTransactions(transactions.name)
                       self.cleanForm()
                      })
                },

            cleanForm(){
                this.formCoin.isNew = true
                this.formCoin.name = ''
                this.formCoin.price = ''
                this.formCoin.quantity = ''
                this.formCoin.title = 'Cadastrar nova transação'
                this.formCoin.button = 'Cadastrar'
            },

          formattedDate(date){
                 return (new Date(date.split('T')[0])).toLocaleDateString("pt-br")
          }

    }
}

createApp(mainContainer).mount('#app')