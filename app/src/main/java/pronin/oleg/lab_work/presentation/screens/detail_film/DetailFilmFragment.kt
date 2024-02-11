package pronin.oleg.lab_work.presentation.screens.detail_film

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowCompat
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import pronin.oleg.lab_work.R
import pronin.oleg.lab_work.databinding.FragmentDetailFilmBinding
import pronin.oleg.lab_work.domain.models.FilmDomainModel
import pronin.oleg.lab_work.util.launchCollect
import pronin.oleg.lab_work.util.repeatOnStart
import javax.inject.Inject
import kotlin.math.abs

@AndroidEntryPoint
class DetailFilmFragment : Fragment() {

    private val args by lazy(LazyThreadSafetyMode.NONE) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(DetailFilmArgs.ARGS_KEY, DetailFilmArgs::class.java)
        } else {
            arguments?.getParcelable(DetailFilmArgs.ARGS_KEY) as? DetailFilmArgs
        }
    }

    @Inject
    lateinit var viewModelFactory: DetailFilmViewModel.Factory
    private val viewModel: DetailFilmViewModel by viewModels {
        DetailFilmViewModel.provideFactory(
            assistedFactory = viewModelFactory,
            args = args
        )
    }

    private var _binding: FragmentDetailFilmBinding? = null
    private val binding get() = _binding!!

    private val navController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailFilmBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().window.let { window ->
            window.statusBarColor = requireContext().getColor(android.R.color.transparent)

            WindowCompat.setDecorFitsSystemWindows(
                window,
                false
            )
        }

        setAppearanceLightStatusBars(false)

        binding.apply {
            back.setOnClickListener { navController.navigateUp() }

            appBar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
                if (abs(verticalOffset) - appBarLayout.totalScrollRange == 0)
                    setAppearanceLightStatusBars(true)
                else if (verticalOffset == 0)
                    setAppearanceLightStatusBars(false)
            }
        }

        viewLifecycleOwner.repeatOnStart {
            launchCollect(viewModel.item) {
                if (it != null)
                    renderFilm(it)
            }
        }
    }

    override fun onDestroyView() {
        _binding = null

        super.onDestroyView()
    }

    private fun setAppearanceLightStatusBars(value: Boolean) {
        WindowCompat.getInsetsController(
            requireActivity().window,
            requireView()
        ).apply {
            isAppearanceLightStatusBars = value
        }
    }

    private fun renderFilm(film: FilmDomainModel) {
        binding.apply {
            banner.apply {
                Glide.with(context)
                    .asBitmap()
                    .load(film.posterUrl)
                    .into(this)
            }

            name.text = film.nameRu

            description.text = film.description

            if (film.genres.isEmpty())
                genresLayout.isGone = true
            else
                genres.text = film.genres.joinToString(separator = ", ") { it.genre }

            if (film.countries.isEmpty())
                countriesLayout.isGone = true
            else
                countries.text = film.countries.joinToString(separator = ", ") { it.country }
        }
    }
}