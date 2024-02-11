package pronin.oleg.lab_work.presentation.screens.list_movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import dagger.hilt.android.AndroidEntryPoint
import pronin.oleg.lab_work.R
import pronin.oleg.lab_work.databinding.FragmentListMovieBinding
import pronin.oleg.lab_work.presentation.decorations.ListRecyclerMarginsDecoration
import pronin.oleg.lab_work.presentation.screens.list_movie.adapter.FilmDiffUtil
import pronin.oleg.lab_work.presentation.screens.list_movie.adapter.FilmListItem
import pronin.oleg.lab_work.presentation.screens.list_movie.adapter.filmListAdapterDelegate
import pronin.oleg.lab_work.presentation.screens.list_movie.adapter.progressBarAdapterDelegate
import pronin.oleg.lab_work.util.launchCollect
import pronin.oleg.lab_work.util.repeatOnStart

@AndroidEntryPoint
class ListFilmsFragment : Fragment() {

    private val viewModel: ListFilmViewModel by viewModels()

    private var _binding: FragmentListMovieBinding? = null
    private val binding get() = _binding!!

    private var _filmAdapter: AsyncListDifferDelegationAdapter<FilmListItem>? = null
    private val filmAdapter get() = requireNotNull(_filmAdapter)

    private val navController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _filmAdapter = AsyncListDifferDelegationAdapter(
            FilmDiffUtil(),
            filmListAdapterDelegate(
                onClick = {}
            ),
            progressBarAdapterDelegate()
        )

        binding.apply {
            title.text = getString(R.string.popular)

            listMovies.apply {
                adapter = filmAdapter
                layoutManager = LinearLayoutManager(requireContext())

                addItemDecoration(
                    ListRecyclerMarginsDecoration.verticalListMargins(
                        firstTop = resources.getDimensionPixelOffset(R.dimen.first_decoration_margin),
                        lastBottom = resources.getDimensionPixelOffset(R.dimen.last_decoration_margin),
                        vertical = resources.getDimensionPixelOffset(R.dimen.vertical_decoration_margin)
                    )
                )
            }
        }

        viewLifecycleOwner.repeatOnStart {
            launchCollect(viewModel.items) {
                if (_filmAdapter != null)
                    filmAdapter.items = it
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        _filmAdapter = null

        super.onDestroyView()
    }
}
