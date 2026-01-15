class GameActivity : AppCompatActivity() {

    private lateinit var viewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        viewModel = ViewModelProvider(this)[GameViewModel::class.java]

        // Contoh skenario: User klik tombol "Pilih A"
        btnChooseA.setOnClickListener {
            // Kita set strategi diskonnya secara dinamis (Polymorphism)
            val strategyA = PercentDiscount(50.0) // Diskon 50%
            val strategyB = BuyNGetFreeDiscount(2, 1) // Beli 2 Gratis 1
            
            viewModel.checkAnswer(50000.0, strategyA, 30000.0, strategyB)
        }

        // Observasi hasil
        viewModel.gameResult.observe(this) { result ->
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
        }
    }
}