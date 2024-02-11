package pronin.oleg.lab_work.presentation.screens.list_films

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.WindowCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import dagger.hilt.android.AndroidEntryPoint
import pronin.oleg.lab_work.R
import pronin.oleg.lab_work.databinding.FragmentListFilmsBinding
import pronin.oleg.lab_work.presentation.decorations.ListRecyclerMarginsDecoration
import pronin.oleg.lab_work.presentation.screens.detail_film.DetailFilmArgs
import pronin.oleg.lab_work.presentation.screens.list_films.adapter.FilmDiffUtil
import pronin.oleg.lab_work.presentation.screens.list_films.adapter.FilmListItem
import pronin.oleg.lab_work.presentation.screens.list_films.adapter.filmListAdapterDelegate
import pronin.oleg.lab_work.presentation.screens.list_films.adapter.progressBarAdapterDelegate
import pronin.oleg.lab_work.util.animNavigate
import pronin.oleg.lab_work.util.launchCollect
import pronin.oleg.lab_work.util.repeatOnStart

@AndroidEntryPoint
class ListFilmsFragment : Fragment() {

    private val viewModel: ListFilmViewModel by viewModels()

    private var _binding: FragmentListFilmsBinding? = null
    private val binding get() = _binding!!

    private var _filmAdapter: AsyncListDifferDelegationAdapter<FilmListItem>? = null
    private val filmAdapter get() = requireNotNull(_filmAdapter)

    private val navController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListFilmsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().window.let { window ->
            window.statusBarColor = requireContext().getColor(R.color.white)

            WindowCompat.setDecorFitsSystemWindows(
                window,
                true
            )

            WindowCompat.getInsetsController(
                window,
                requireView()
            ).isAppearanceLightStatusBars = true
        }

        _filmAdapter = AsyncListDifferDelegationAdapter(
            FilmDiffUtil(),
            filmListAdapterDelegate(
                onClick = {
                    navController.animNavigate(
                        R.id.action_ListFilmsFragment_to_DetailFilmFragment,
                        args = bundleOf(
                            DetailFilmArgs.ARGS_KEY to DetailFilmArgs(filmId = it.id)
                        )
                    )
                }
            ),
            progressBarAdapterDelegate()
        )

        binding.apply {
            swipeRefreshLayout.setOnRefreshListener { viewModel.onSwipeRefresh() }

            errorLayout.repeatButton.setOnClickListener {
                viewModel.initializeItems()
            }

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

            launchCollect(viewModel.isInProgress) {
                binding.apply {
                    listMovies.isVisible = !it
                    progressLayout.root.isVisible = it
                }
            }

            launchCollect(viewModel.isRefreshing) {
                binding.apply {
                    listMovies.isVisible = true
                    swipeRefreshLayout.isRefreshing = it
                }
            }

            launchCollect(viewModel.isError) {
                binding.apply {
                    swipeRefreshLayout.isGone = it
                    errorLayout.root.isGone = !it
                }
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        _filmAdapter = null

        super.onDestroyView()
    }
}
